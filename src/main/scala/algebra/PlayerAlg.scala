package algebra

import cats.data.EitherT
import cats.effect.Effect
import domain.{Card, Deck, GameError, GameType}

trait PlayerAlg {
  def playHand[F[_] : Effect](deck: Deck, playerCard: Option[Card], gameType: GameType): EitherT[F, GameError, (Deck, Option[Card])]
}
