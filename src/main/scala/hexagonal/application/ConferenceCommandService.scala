package hexagonal.application

import hexagonal.domain._

class ConferenceCommandService(conferenceRepository: ConferenceRepository) {
  def registerAttendee(conferenceId: Long,
                       param: AttendeeRegistrationParam): IfFound[AttendeeRegistrationResult] = {
    val maybeConference = conferenceRepository.getById(conferenceId)
    maybeConference match {
      case None => NotFound
      case Some(conference) => {
        val registrationResult = conference.register(param)
        Result[AttendeeRegistrationResult](registrationResult)
      }
    }
  }
}






