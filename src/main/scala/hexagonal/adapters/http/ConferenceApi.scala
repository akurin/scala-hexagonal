package hexagonal.adapters.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import hexagonal.application._
import hexagonal.domain._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

class ConferenceApi(commandService: ConferenceCommandService,
                    queryService: ConferenceQueryService) {

  import hexagonal.adapters.http.ConferenceJsonProtocol._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  val route: Route = pathPrefix("api") {
    path("conferences" / LongNumber / "attendees") { conferenceId =>
      (post & entity(as[AttendeeRegistrationParam]) & extractRequest) { (attendee, request) =>
        val notFoundOrResult = commandService.registerAttendee(conferenceId, attendee)

        notFoundOrResult match {
          case NotFound => complete(StatusCodes.NotFound)
          case Result(AttendeeRegistered(conference, attendeeId)) =>
            val locationHeader = Location(request.uri.withPath(request.uri.path / attendeeId.toString))
            respondWithHeader(locationHeader)(complete(StatusCodes.Created))
          case Result(FullyBooked) =>
            complete(StatusCodes.BadRequest, "The conference is fully booked")
        }
      }
    } ~
      path("conferences" / LongNumber / "attendees" / LongNumber) { (conferenceId, attendeeId) =>
        get {
          val maybeAttendee = queryService.getAttendee(conferenceId, attendeeId)
          maybeAttendee match {
            case None => complete(StatusCodes.NotFound)
            case Some(attendee) =>
              val attendeeView = AttendeeView(attendee.name)
              complete(attendeeView)
          }
        }
      }
  }
}

case class AttendeeView(name: String)

object ConferenceJsonProtocol extends DefaultJsonProtocol {
  implicit val attendeeRegistrationParamFormat: RootJsonFormat[AttendeeRegistrationParam] = jsonFormat1(AttendeeRegistrationParam)
  implicit val attendeeViewFormat: RootJsonFormat[AttendeeView] = jsonFormat1(AttendeeView)
}