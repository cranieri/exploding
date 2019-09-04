package algebra

import cats.data.EitherT
import cats.effect.Effect
import domain.{Card, Deck, GameError, GameType}

trait GameAlg {
  def play[F[_] : Effect](deck: Deck, playerCard: Option[Card], gameType: GameType): EitherT[F, GameError, (Card, Deck)]
}
