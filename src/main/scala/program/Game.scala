package program

import algebra.{ CardDrawerAlg, ConsoleAlg, RandomizerAlg }
import cats.data.EitherT
import cats.effect.{ Effect, Sync }
import domain.{ Card, Deck, GameExit, GameType }
import interpreter.CommandLineResponseHandler

class Game[F[_]: Effect](
    gameType: GameType[F],
    console: ConsoleAlg[F],
    cardDrawer: CardDrawerAlg,
    randomizer: RandomizerAlg
) {
  def play: EitherT[F, GameExit, (Card, Deck)] = {
    def playGame(deck: Deck, playerCard: Option[Card]): EitherT[F, GameExit, (Card, Deck)] =
      for {
        _ <- EitherT.right(
              console.putStrLn("Press 'd' to draw a card (any other key to exit): ")
            )
        n <- EitherT.right(console.readLn)
        _ <- EitherT(
              Sync[F].pure(new CommandLineResponseHandler(console).handleResponse(n))
            )
        c <- EitherT(Sync[F].pure(cardDrawer.draw(deck)))
        d <- gameType.check(c._1, playerCard, c._2)
        p <- playGame(d._1, d._2)
      } yield p

    playGame(gameType.deck, gameType.playerCard)
  }
}
