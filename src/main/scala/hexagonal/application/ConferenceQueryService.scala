package hexagonal.application

import hexagonal.domain.{Attendee, ConferenceRepository}

class ConferenceQueryService(conferenceRepository: ConferenceRepository) {
  def getAttendee(conferenceId: Long, attendeeId: Long): Option[Attendee] = {
    for {
      conference <- conferenceRepository.getById(conferenceId)
      attendee <- conference.getAttendee(attendeeId)
    } yield attendee
  }
}
