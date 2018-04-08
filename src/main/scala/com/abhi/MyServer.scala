package com.abhi

import cats.effect.IO
import cats.syntax.semigroupk._

import fs2.{StreamApp, Stream}
import fs2.StreamApp.ExitCode

import org.log4s.getLogger
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.HttpService
import scala.concurrent.ExecutionContext.Implicits.global
import org.http4s.rho.swagger.syntax.io._
import org.http4s.server.middleware.CORS
import org.http4s.rho.swagger.syntax.{io => ioSwagger}

object Main extends StreamApp[IO] {
  private val logger = getLogger
  logger.info(s"Starting server.....")
  def stream(args: List[String], requestShutdown: IO[Unit]) : Stream[IO, ExitCode] = {
    val rhoService = HelloService.helloRoute[IO]
    val middleware = createRhoMiddleware()
    val service = CORS(rhoService.toService(middleware))
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(service, "/")
      .serve
  }
}

