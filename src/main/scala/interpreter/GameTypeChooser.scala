package interpreter

import algebra.GameTypeChooserAlg
import cats.data.EitherT
import cats.effect.{Sync}
import domain.{GameExit, GameType}
import cats.implicits._


object GameTypeChooser extends GameTypeChooserAlg {
  def choose[F[_]: Sync]: EitherT[F, GameExit, GameType] = {
    EitherT(for {
      _ <- Sync[F].delay(println("Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit): "))
      n <- Sync[F].delay(scala.io.StdIn.readLine)
      t <- Sync[F].pure(CommandLineResponseHandler.handleResponseGameType(n))
    } yield t)
  }
}
