package interpreter

import algebra.PlayerAlg
import cats.data.EitherT
import cats.effect.{Effect, Sync}
import domain.{Card, Deck, GameError}

object Player extends PlayerAlg {
  def playHand[F[_] : Effect](deck: Deck, playerCard: Option[Card.Value]): EitherT[F, GameError, (Deck, Option[Card.Value])] = {
    for {
      _ <- EitherT.right(Sync[F].delay(println("Press 'd' to draw a card (q to exit): ")))
      n <- EitherT.right(Sync[F].delay(scala.io.StdIn.readLine))
      _ <- EitherT(Sync[F].pure(CommandLineResponseHandler.handleResponse(n)))
      c <- EitherT(Sync[F].pure(CardDrawer.draw(deck)))
      d <- EitherT(Sync[F].pure(CardChecker.check(c._1, playerCard, c._2)))
    } yield d
  }
}
