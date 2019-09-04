package interpreter

import algebra.GameAlg
import cats.data.EitherT
import cats.effect.Effect
import domain.{Card, Deck, GameError}

object Game extends GameAlg {
  def play[F[_] : Effect](deck: Deck, playerCard: Option[Card.Value]): EitherT[F, GameError, (Card.Value, Deck)] = {
    def playGame(deck: Deck, playerCard: Option[Card.Value]): EitherT[F, GameError, (Card.Value, Deck)] = {
      val game = Player.playHand[F](deck, playerCard)

      for {
        c <- game
        p <- playGame(c._1, c._2)
      } yield p
    }

    playGame(deck, playerCard)
  }
}
