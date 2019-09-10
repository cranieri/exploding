package algebra

import domain.{ Card, Deck, GameExit }

trait CardDrawerAlg {
  def draw(deck: Deck): Either[GameExit, (Card, Deck)]
}
