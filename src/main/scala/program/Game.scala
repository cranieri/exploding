package program

import cats.data.EitherT
import cats.effect.{Effect, Sync}
import domain.{Card, Deck, GameError, GameType}
import interpreter.{CardChecker, CardDrawer, CommandLineResponseHandler}

object Game {
  def play[F[_] : Effect](gameType: GameType): EitherT[F, GameError, (Card, Deck)] = {
    println(s"you're playing: $gameType")

    def playGame(deck: Deck, playerCard: Option[Card]): EitherT[F, GameError, (Card, Deck)] = {
      for {
        _ <- EitherT.right(Sync[F].delay(println("Press 'd' to draw a card (q to exit): ")))
        n <- EitherT.right(Sync[F].delay(scala.io.StdIn.readLine))
        _ <- EitherT(Sync[F].pure(CommandLineResponseHandler.handleResponse(n)))
        c <- EitherT(Sync[F].pure(CardDrawer.draw(deck)))
        d <- EitherT(Sync[F].pure(CardChecker(gameType).check(c._1, playerCard, c._2)))
        p <- playGame(d._1, d._2)
      } yield p
    }

    playGame(gameType.deck, gameType.playerCard)
  }
}
