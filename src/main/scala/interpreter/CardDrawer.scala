package interpreter

import algebra.CardDrawerAlg
import domain.{Card, Deck, DrawError, GameError}

object CardDrawer extends CardDrawerAlg {
  def draw(deck: Deck): Either[GameError, (Card.Value, Deck)] = {
    println(deck.cards)
    deck.cards match {
      case h :: t => Right(h, Deck(t))
      case _ => Left(DrawError)
    }
  }
}
