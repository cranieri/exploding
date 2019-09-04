package interpreter

import algebra.CardCheckerAlg
import domain.{BasicRouletteRuse, Card, Deck, DefuseCards, GameError, GameType}

trait CardChecker extends CardCheckerAlg {
  def check(card: Card, playerCard: Option[Card], deck: Deck, gameType: GameType): Either[GameError, (Deck, Option[Card])] = ???
}

object CardChecker extends CardChecker {
  def apply(gameType: GameType): CardChecker = {
    gameType match {
      case BasicRouletteRuse => BasicRouletteRuseCardChecker
      case DefuseCards => DefuseCardsCardChecker
    }
  }
}
