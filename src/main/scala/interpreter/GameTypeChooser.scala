package interpreter

import algebra.{ ConsoleAlg, GameTypeChooserAlg, RandomizerAlg }
import cats.data.EitherT
import cats.effect.Sync
import domain.{
  BasicRouletteRuse,
  Blank,
  Deck,
  Defuse,
  DefuseCards,
  Explosive,
  GameExit,
  GameType,
  Quit
}
import cats.implicits._

class GameTypeChooser[F[_]: Sync](console: ConsoleAlg[F], randomizer: RandomizerAlg)
    extends GameTypeChooserAlg[F] {
  def choose: EitherT[F, GameExit, GameType[F]] =
    EitherT(for {
      _ <- console.putStrLn(
            "Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit): "
          )
      n <- console.readLn
      t <- Sync[F].pure(
            handleResponseGameType(n)
          )
    } yield t)

  def handleResponseGameType(r: String): Either[GameExit, GameType[F]] =
    r match {
      case "1" =>
        Right(
          BasicRouletteRuse(console, randomizer.run(Deck(List.fill(46)(Blank) :+ Explosive)), None)
        )
      case "2" =>
        Right(
          DefuseCards(
            randomizer.run(Deck((List.fill(46)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive)),
            Some(Defuse),
            console
          )
        )
      case _ => Left(Quit)
    }
}
