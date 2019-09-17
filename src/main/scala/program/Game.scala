package program

import algebra.{ CardCheckerAlg, CardDrawerAlg, ConsoleAlg }
import cats.data.EitherT
import cats.effect.{ Effect, Sync }
import domain.{ Card, Deck, GameExit, GameType }
import interpreter.{ CommandLineResponseHandler }

class Game(
    gameType: GameType,
    console: ConsoleAlg,
    cardDrawer: CardDrawerAlg,
    cardChecker: CardCheckerAlg
) {
  def play[F[_]: Effect]: EitherT[F, GameExit, (Card, Deck)] = {
    def playGame(deck: Deck, playerCard: Option[Card]): EitherT[F, GameExit, (Card, Deck)] =
      for {
        _ <- EitherT.right(
              console.putStrLn[F]("Press 'd' to draw a card (any other key to exit): ")
            )
        n <- EitherT.right(console.readLn[F])
        _ <- EitherT(Sync[F].pure(CommandLineResponseHandler.handleResponse(n)))
        c <- EitherT(Sync[F].pure(cardDrawer.draw(deck)))
        d <- cardChecker.check(c._1, playerCard, c._2)
        p <- playGame(d._1, d._2)
      } yield p

    playGame(gameType.deck, gameType.playerCard)
  }
}
