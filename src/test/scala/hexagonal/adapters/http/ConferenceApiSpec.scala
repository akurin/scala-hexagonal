package hexagonal.adapters.http

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Location
import hexagonal.cake._
import hexagonal.domain._
import org.scalatest._

class ConferenceApiSpec extends path.FunSpec with ImprovedScalatestRouteTest with Matchers {
  val modules = new HttpModule
    with ApplicationModule
    with InMemoryRepositoriesModule

  val topRoute = modules.conferenceApi.route

  describe("POST /api/conferences/{conderenceId}/attendees") {
    describe("conference does not exist") {
      it("returns NotFound") {
        Post(
          s"/api/conferences/123/attendees",
          HttpEntity(ContentTypes.`application/json`, """{"name": "John Doe"}""")) ~> topRoute ~> check {
          status shouldBe NotFound
        }
      }
    }

    describe("conference is partially booked") {
      val conference = ConferenceMother.createPartiallyBooked()
      modules.conferenceRepository.save(conference)

      it("returns Created and location header") {
        val entity = HttpEntity(ContentTypes.`application/json`, """{"name": "John Doe"}""")

        Post(s"/api/conferences/${conference.id}/attendees", entity) ~> topRoute ~> check {
          status shouldBe Created

          response.header[Location] match {
            case Some(Location(uri)) =>
              uri.toString() should endWith regex s"/api/conferences/${conference.id}/attendees/\\d+".r
            case _ => fail("location header has not been not found")
          }
        }
      }
    }

    describe("conference is fully booked") {
      val conference = ConferenceMother.createFullyBooked()
      modules.conferenceRepository.save(conference)

      it("returns BadRequest and message") {
        val entity = HttpEntity(ContentTypes.`application/json`, """{"name": "John Doe"}""")

        Post(s"/api/conferences/${conference.id}/attendees", entity) ~> topRoute ~> check {
          status shouldBe BadRequest
          responseAs[String] should not be (empty)
        }
      }
    }
  }

  describe("GET conferences/{conferenceId}/attendees/{attendeeId}") {
    describe("conference does not exist") {
      it("returns NotFound") {
        Get("/api/conferences/123/attendees/345") ~> topRoute ~> check {
          status shouldBe NotFound
        }
      }
    }

    describe("attendee exists") {
      val conference = ConferenceMother.createPartiallyBooked()
      val param = new AttendeeRegistrationParam("John Doe")
      val AttendeeRegistered(conferenceWithAttendee, attendeeId) = conference.register(param)
      modules.conferenceRepository.save(conferenceWithAttendee)

      it("returns OK") {
        Get(s"/api/conferences/${conference.id}/attendees/$attendeeId") ~> topRoute ~> check {
          status shouldBe OK
          responseEntity.contentType shouldBe ContentTypes.`application/json`
          responseAs[String] should not be (empty)
        }
      }
    }
  }
}


