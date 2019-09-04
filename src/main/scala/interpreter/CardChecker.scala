package interpreter

import algebra.CardCheckerAlg
import domain.{Card, Deck, Defuse, Exploded, Explosive, GameError}

object CardChecker extends CardCheckerAlg {
  def check(card: Card, playerCard: Option[Card], deck: Deck): Either[GameError, (Deck, Option[Card])] = {
    println(card)
    card match {
      case e: Explosive.type => {
        playerCard match {
          case Some(c: Defuse.type) => Right(Deck(util.Random.shuffle(deck.cards :+ card)), None)
          case _ => Left(Exploded)
        }
      }
      case _ => Right(deck, playerCard)
    }
  }
}
