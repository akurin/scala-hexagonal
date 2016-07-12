package hexagonal.cake

import com.softwaremill.macwire._
import hexagonal.application._
import hexagonal.domain.ConferenceRepository

trait ApplicationModule {
  lazy val conferenceCommandService = wire[ConferenceCommandService]
  lazy val conferenceQueryService = wire[ConferenceQueryService]

  def conferenceRepository: ConferenceRepository
}
