package io.github.mrdziuban.http4sgziptest

import fs2.{Task, Stream}
import java.util.concurrent.{ExecutorService, Executors}
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.GZip
import org.http4s.util.StreamApp
import scala.util.Properties.envOrNone

object BlazeExample extends StreamApp {

  val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip   : String           = "0.0.0.0"
  val pool : ExecutorService  = Executors.newCachedThreadPool()

  override def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(GZip(HelloWorld.service))
      .withServiceExecutor(pool)
      .serve
}
