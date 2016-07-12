package hexagonal.domain

class Conference private(val id: Long, totalSeats: Int, val attendees: Seq[Attendee]) {
  def register(params: AttendeeRegistrationParam): AttendeeRegistrationResult = {
    val enoughSeats = attendees.length + 1 <= totalSeats
    if (enoughSeats) {
      val attendee = new Attendee(nextAttendeeId, params)
      val conference = copy(attendees = attendees :+ attendee)
      AttendeeRegistered(conference, attendee.id)
    }
    else
      FullyBooked
  }

  private def nextAttendeeId = attendees.length + 1

  private def copy(id: Long = id,
                   totalSeats: Int = totalSeats,
                   attendees: Seq[Attendee] = attendees) = {
    new Conference(id = id, totalSeats = totalSeats, attendees = attendees)
  }

  def getAttendee(attendeeId: Long): Option[Attendee] = attendees.find(_.id == attendeeId)
}

object Conference {
  def create(id: Long, totalSeats: Int): Conference = {
    new Conference(id, totalSeats, Seq.empty)
  }
}
