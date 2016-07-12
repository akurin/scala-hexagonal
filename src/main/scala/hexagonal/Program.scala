package hexagonal

import com.typesafe.config.ConfigFactory
import hexagonal.cake._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

object Program {
  def main(args: Array[String]) {
    val modules = new HttpModule
      with ApplicationModule
      with InMemoryRepositoriesModule

    val config = ConfigFactory.load()
    val interface = config.getString("http.interface")
    val port = config.getInt("http.port")

    val startedAppFuture = new AppStarter(modules, interface, port).start()
    val app = Await.result(startedAppFuture, Duration.Inf)

    println(s"Server online at http://$interface:$port/\nPress RETURN to stop...")
    StdIn.readLine()
    app.stop()
  }
}
