package algebra

import domain.{Card, Deck, GameError}

trait CardCheckerAlg {
  def check(card: Card, playerCard: Option[Card], deck: Deck): Either[GameError, (Deck, Option[Card])]
}
