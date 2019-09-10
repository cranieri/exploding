package algebra

import cats.data.EitherT
import cats.effect.Sync
import domain.{ Card, Deck, Defuse, GameExit }

trait CardCheckerAlg {
  def check[F[_]: Sync](
      card: Card,
      playerCard: Option[Defuse.type],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Defuse.type])]
}
