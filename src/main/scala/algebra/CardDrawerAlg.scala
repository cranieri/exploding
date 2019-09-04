package algebra

import domain.{Card, Deck, GameError}

trait CardDrawerAlg {
  def draw(deck: Deck): Either[GameError, (Card.Value, Deck)]
}
