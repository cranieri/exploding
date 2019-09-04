package algebra

import domain.{Card, Deck, GameError}

trait CardCheckerAlg {
  def check(card: Card.Value, playerCard: Option[Card.Value], deck: Deck): Either[GameError, (Deck, Option[Card.Value])]
}
