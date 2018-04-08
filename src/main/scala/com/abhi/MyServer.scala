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

import org.http4s.rho.swagger.syntax.{io => ioSwagger}

object Main extends StreamApp[IO] {
  private val logger = getLogger
  val port : Int = Option(System.getenv("HTTP_PORT"))
    .map(_.toInt)
    .getOrElse(8080)
  
  logger.info(s"Starting server at port: $port")

  def stream(args: List[String], requestShutdown: IO[Unit]) : Stream[IO, ExitCode] = {
    val middleware = createRhoMiddleware()
    val myService: HttpService[IO] = new MyService[IO](ioSwagger) {}.toService(middleware)
    BlazeBuilder[IO]
      .mountService(StaticContentService.routes combineK myService)
      .bindLocal(port)
      .serve    
  }
}

