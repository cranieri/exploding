package interpreter

import algebra.CardCheckerAlg
import cats.data.EitherT
import cats.effect.Sync
import domain.{ BasicRouletteRuse, Card, Deck, Defuse, DefuseCards, GameExit, GameType }

trait CardChecker extends CardCheckerAlg {
  def check[F[_]: Sync](
      card: Card,
      playerCard: Option[Defuse.type],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Defuse.type])] = ???
}

object CardChecker extends CardChecker {
  def apply[F[_]: Sync](gameType: GameType): CardChecker =
    gameType match {
      case BasicRouletteRuse => BasicRouletteRuseCardChecker
      case DefuseCards       => DefuseCardsCardChecker
    }
}
