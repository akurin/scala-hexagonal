package hexagonal

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import hexagonal.cake.HttpModule

import scala.concurrent.Future

class AppStarter(httpModule: HttpModule, interface: String, port: Int) {
  def start(): Future[StartedApp] = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    Http()
      .bindAndHandle(httpModule.conferenceApi.route, interface, port)
      .map(serverBinding => new StartedApp(serverBinding, system))
  }
}
