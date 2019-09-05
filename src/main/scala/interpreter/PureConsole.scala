package interpreter

import algebra.ConsoleAlg
import cats.effect.{Effect, Sync}

object Console extends ConsoleAlg {

  def putStrLn[F[_]: Effect](s:String): Unit = Sync[F].delay(println("Press 'd' to draw a card (any other key to exit): "))
  def readLn[F[_]: Effect]: Unit = Sync[F].delay(scala.io.StdIn.readLine)
}
