package algebra

import domain.{Card, Deck, GameError, GameType}

trait CardCheckerAlg {
  def check(card: Card, playerCard: Option[Card], deck: Deck, gameType: GameType): Either[GameError, (Deck, Option[Card])]
}
