package com.abhi

// cats imports
import cats.effect.Effect
import cats.Monad

// api imports
import org.http4s.rho.RhoService

// swagger imports
import org.http4s.rho.swagger.SwaggerSyntax

// json imports
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods

abstract class MyService[F[+_]: Effect](swaggerSyntax: SwaggerSyntax[F])(implicit f: Monad[F]) extends RhoService[F] { 
  
  GET / "hello" / pathVar[String] |>> { name: String =>
    Ok(s"Hello $name")
  }
}