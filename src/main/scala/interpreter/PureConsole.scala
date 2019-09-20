package interpreter

import algebra.ConsoleAlg
import cats.effect.Sync

class PureConsole[F[_]: Sync](r: => String = scala.io.StdIn.readLine) extends ConsoleAlg[F] {

  def putStrLn(s: String): F[Unit] = Sync[F].delay(println(s))
  def readLn: F[String]            = Sync[F].delay(r)
}
