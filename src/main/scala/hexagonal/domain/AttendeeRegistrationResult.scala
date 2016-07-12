package hexagonal.domain

sealed abstract class AttendeeRegistrationResult

case class AttendeeRegistered(conference: Conference, attendeeId: Long) extends AttendeeRegistrationResult

case object FullyBooked extends AttendeeRegistrationResult