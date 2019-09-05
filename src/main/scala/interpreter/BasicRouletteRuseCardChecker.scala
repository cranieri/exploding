package interpreter

import cats.data.EitherT
import cats.effect.{Effect, Sync}
import domain._

object BasicRouletteRuseCardChecker extends CardChecker {
  override def check[F[_]: Effect](card: Card, playerCard: Option[Card], deck: Deck): EitherT[F, GameExit, (Deck, Option[Card])] = {
    card match {
      case _: Explosive.type => {
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"Sorry! You drew a $card card. You exploded!")))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Exploded)))
        } yield m
      }
      case _ =>
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"Well done! You drew a $card card. Keep drawing")))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Right(deck, playerCard)))
        } yield m
    }
  }
}


