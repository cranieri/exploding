package interpreter

import domain._

object DefuseCardsCardChecker extends CardChecker {
  override def check(card: Card, playerCard: Option[Card], deck: Deck): Either[GameError, (Deck, Option[Card])] = {
    println(card)
    card match {
      case _: Explosive.type => {
        playerCard match {
          case Some(_: Defuse.type) => Right(Deck(util.Random.shuffle(deck.cards :+ card)), None)
          case _ => Left(Quit)
        }
      }
      case _ => Right(deck, playerCard)
    }
  }
}


