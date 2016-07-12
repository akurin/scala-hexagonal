package hexagonal.domain

trait AttendeeRepository {
  def getById(id: Long): Option[AttendeeRegistrationParam]

  def Save(user: AttendeeRegistrationParam): Unit
}

