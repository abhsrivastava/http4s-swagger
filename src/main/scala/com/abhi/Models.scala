package com.abhi

import cats.Applicative
import cats.effect.Sync
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}

case class ResponseMessage(message: String)
object ResponseMessage{
  implicit val responseMessageEnc: Encoder[ResponseMessage] = deriveEncoder[ResponseMessage]
  implicit val responseMessageDec : Decoder[ResponseMessage] = deriveDecoder[ResponseMessage]
  implicit def responseMessageEncoder[F[_]: Applicative]: EntityEncoder[F, ResponseMessage] =
    jsonEncoderOf[F, ResponseMessage](EntityEncoder[F, String], Applicative[F], responseMessageEnc)
  implicit def responseMessageEntityDecoder[F[_]: Sync]: EntityDecoder[F, ResponseMessage] =
    jsonOf[F, ResponseMessage](Sync[F], responseMessageDec)
}