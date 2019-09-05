package interpreter

import algebra.CardCheckerAlg
import cats.data.EitherT
import cats.effect.Effect
import domain.{BasicRouletteRuse, Card, Deck, DefuseCards, GameExit, GameType}

trait CardChecker extends CardCheckerAlg {
  def check[F[_]: Effect](card: Card, playerCard: Option[Card], deck: Deck): EitherT[F, GameExit, (Deck, Option[Card])] = ???
}

object CardChecker extends CardChecker {
  def apply[F[_]: Effect](gameType: GameType): CardChecker = {
    gameType match {
      case BasicRouletteRuse => BasicRouletteRuseCardChecker
      case DefuseCards => DefuseCardsCardChecker
    }
  }
}
