package hexagonal

import akka.actor.ActorSystem
import akka.http.scaladsl.Http.ServerBinding

import scala.concurrent.Future

class StartedApp(serverBinding: ServerBinding, actorSystem: ActorSystem) {
  def stop(): Future[Unit] = {
    implicit val executionContext = actorSystem.dispatcher
    serverBinding
      .unbind()
      .flatMap(_ => actorSystem.terminate())
      .map(_ => ())
  }
}
