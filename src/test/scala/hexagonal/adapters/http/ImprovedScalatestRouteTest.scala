package hexagonal.adapters.http

import akka.http.scaladsl.testkit.{RouteTest, ScalatestUtils, TestFrameworkInterface}
import org.scalatest.exceptions.TestFailedException

trait ImprovedScalatestRouteTest extends RouteTest with TestFrameworkInterface with ScalatestUtils {
  def failTest(msg: String) = throw new TestFailedException(msg, 11)
}
