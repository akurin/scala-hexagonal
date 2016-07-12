package hexagonal.domain

object ConferenceMother {
  private val defaultTotalSeats = 10

  def createPartiallyBooked(conferenceId: Option[Long] = None): Conference = {
    val conferenceIdOrDefault = conferenceId.getOrElse(someConferenceId())

    val emptyConference = Conference.create(conferenceIdOrDefault, defaultTotalSeats)
    registerAttendees(emptyConference, 5)
  }

  private def someConferenceId() = util.Random.nextInt(Int.MaxValue).toLong

  private def registerAttendees(conference: Conference,
                                numberOfAttendees: Int): Conference = {
    (1 to numberOfAttendees).foldLeft(conference) { (conference, attendeeNum) =>
      val registrationParams = new AttendeeRegistrationParam(s"attendee $attendeeNum")
      val AttendeeRegistered(conferenceWithAttendee, attendeeId) = conference.register(registrationParams)
      conferenceWithAttendee
    }
  }

  def createFullyBooked(conferenceId: Option[Long] = None): Conference = {
    val conferenceIdOrDefault = conferenceId.getOrElse(someConferenceId())

    val emptyConference = Conference.create(conferenceIdOrDefault, defaultTotalSeats)
    registerAttendees(emptyConference, defaultTotalSeats)
  }
}
