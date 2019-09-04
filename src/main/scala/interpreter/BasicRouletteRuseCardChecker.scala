package interpreter

import domain._

object BasicRouletteRuseCardChecker extends CardChecker {
  override def check(card: Card, playerCard: Option[Card], deck: Deck): Either[GameError, (Deck, Option[Card])] = {
    println(card)
    card match {
      case _: Explosive.type => Left(Exploded)
      case _ => Right(deck, playerCard)
    }
  }
}


