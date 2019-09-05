package interpreter

import algebra.ConsoleAlg
import cats.effect.{Effect, Sync}

object PureConsole extends ConsoleAlg {

  def putStrLn[F[_]: Effect](s:String): F[Unit] = Sync[F].delay(println(s))
  def readLn[F[_]: Effect]: F[String] = Sync[F].delay(scala.io.StdIn.readLine)
}
