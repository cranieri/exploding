package interpreter

import algebra.CardCheckerAlg
import domain.{Card, Deck, Exploded, GameError}

object CardChecker extends CardCheckerAlg {
  def check(card: Card.Value, playerCard: Option[Card.Value], deck: Deck): Either[GameError, (Deck, Option[Card.Value])] = {
    println(card)
    card match {
      case e if e.equals(Card.Explosive) => {
        playerCard match {
          case Some(c) if c == Card.Defuse => Right(Deck(util.Random.shuffle(deck.cards :+ card)), None)
          case _ => Left(Exploded)
        }
      }
      case _ => Right(deck, playerCard)
    }
  }
}
