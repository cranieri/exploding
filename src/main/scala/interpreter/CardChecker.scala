package interpreter

import algebra.CardCheckerAlg
import domain.{BasicRouletteRuse, Card, Deck, Defuse, DefuseCards, Exploded, Explosive, GameError, GameType, Quit}

object CardChecker extends CardCheckerAlg {
  def check(card: Card, playerCard: Option[Card], deck: Deck, gameType: GameType): Either[GameError, (Deck, Option[Card])] = {
    println(card)
    card match {
      case _: Explosive.type => {
        playerCard match {
          case Some(_: Defuse.type) if (gameType == DefuseCards) => Right(Deck(util.Random.shuffle(deck.cards :+ card)), None)
          case _ if (gameType == DefuseCards) => Left(Quit)
          case _ => Left(Exploded)
        }
      }
      case _ => Right(deck, playerCard)
    }
  }
}
