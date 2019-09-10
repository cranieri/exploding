package interpreter

import algebra.ConsoleAlg
import cats.effect.Sync

object PureConsole extends ConsoleAlg {

  def putStrLn[F[_]: Sync](s: String): F[Unit] = Sync[F].delay(println(s))
  def readLn[F[_]: Sync]: F[String]            = Sync[F].delay(scala.io.StdIn.readLine)
}
