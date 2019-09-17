package interpreter

import algebra.CardCheckerAlg
import cats.data.EitherT
import cats.effect.Sync
import domain.{ BasicRouletteRuse, Card, Deck, DefuseCards, GameExit, GameType }

trait CardChecker extends CardCheckerAlg {
  def check[F[_]: Sync](
      card: Card,
      playerCard: Option[Card],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Card])] = ???
}

object CardChecker extends CardChecker {
  def apply[F[_]: Sync](gameType: GameType): CardChecker =
    gameType match {
      case BasicRouletteRuse(_) => BasicRouletteRuseCardChecker
      case DefuseCards(_)       => DefuseCardsCardChecker
    }
}
