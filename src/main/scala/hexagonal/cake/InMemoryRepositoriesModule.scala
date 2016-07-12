package hexagonal.cake

import com.softwaremill.macwire._
import hexagonal.adapters.persistance.inmemory._
import hexagonal.domain.ConferenceRepository

trait InMemoryRepositoriesModule {
  lazy val conferenceRepository: ConferenceRepository = wire[InMemoryConferenceRepository]
}



