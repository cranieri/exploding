package interpreter

import algebra.GameAlg
import cats.data.EitherT
import cats.effect.Effect
import domain.{Card, Deck, GameError}

object Game extends GameAlg {
  def play[F[_] : Effect](deck: Deck, playerCard: Option[Card]): EitherT[F, GameError, (Card, Deck)] = {
    def playGame(deck: Deck, playerCard: Option[Card]): EitherT[F, GameError, (Card, Deck)] = {
      val game = Player.playHand[F](deck, playerCard)

      for {
        c <- game
        p <- playGame(c._1, c._2)
      } yield p
    }

    playGame(deck, playerCard)
  }
}
