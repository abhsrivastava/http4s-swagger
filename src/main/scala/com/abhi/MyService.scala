package com.abhi

import cats.Applicative
import cats.effect.Effect
import org.http4s.rho.RhoService
import org.http4s.rho.swagger.SwaggerSyntax
import org.http4s.Uri
import scala.concurrent.ExecutionContext
import shapeless._
import cats.implicits._


object HelloService {
  def helloRoute[F[_]](implicit F: Effect[F], ec: ExecutionContext) : RhoService[F] = {
    val swaggerSyntax = new SwaggerSyntax[F] {}
    import swaggerSyntax._
    new RhoService[F]{
      "This is hello API" ** 
      GET / "hello" / pathVar[String]("name") |>> { name: String => 
        Ok(ResponseMessage(s"Hello $name"))
      }
    }
  }
}