package hexagonal.domain

class Attendee(val id: Long, attendeeDto: AttendeeRegistrationParam) {
  val name = attendeeDto.name
}
