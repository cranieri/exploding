package algebra

import cats.data.EitherT
import cats.effect.Effect
import domain.{Card, Deck, GameExit}

trait CardCheckerAlg {
  def check[F[_]: Effect](card: Card, playerCard: Option[Card], deck: Deck): EitherT[F, GameExit, (Deck, Option[Card])]
}
