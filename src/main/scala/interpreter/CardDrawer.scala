package interpreter

import algebra.CardDrawerAlg
import domain.{ Card, Deck, DrawExit, GameExit }

object CardDrawer extends CardDrawerAlg {
  def draw(deck: Deck): Either[GameExit, (Card, Deck)] =
    deck.cards match {
      case h :: t => Right(h, Deck(t))
      case _      => Left(DrawExit)
    }
}
