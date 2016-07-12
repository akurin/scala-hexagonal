package hexagonal.adapters.persistance.inmemory

import hexagonal.domain.{Conference, ConferenceRepository}

import scala.collection.concurrent.TrieMap

class InMemoryConferenceRepository extends ConferenceRepository {
  val map = new TrieMap[Long, Conference]()

  override def getById(id: Long): Option[Conference] = map.get(id)

  override def save(conference: Conference): Unit = {
    map(conference.id) = conference
  }
}
