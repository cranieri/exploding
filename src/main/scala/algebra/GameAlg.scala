package algebra

import cats.data.EitherT
import cats.effect.Effect
import domain.{Card, Deck, GameError}

trait GameAlg {
  def play[F[_] : Effect](deck: Deck, playerCard: Option[Card.Value]): EitherT[F, GameError, (Card.Value, Deck)]
}
