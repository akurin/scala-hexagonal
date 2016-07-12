package hexagonal.cake

import hexagonal.adapters.http.ConferenceApi
import hexagonal.application.{ConferenceCommandService, ConferenceQueryService}
import com.softwaremill.macwire._

trait HttpModule {
  lazy val conferenceApi: ConferenceApi = wire[ConferenceApi]

  def conferenceCommandService: ConferenceCommandService

  def conferenceQueryService: ConferenceQueryService
}

