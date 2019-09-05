package algebra

import cats.effect.Effect

trait ConsoleAlg {
  def putStrLn[F[_] : Effect](s: String): F[Unit]

  def readLn[F[_] : Effect]: F[String]
}
