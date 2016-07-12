package hexagonal.domain

trait ConferenceRepository {
  def getById(id: Long): Option[Conference]

  def save(conference: Conference): Unit
}
