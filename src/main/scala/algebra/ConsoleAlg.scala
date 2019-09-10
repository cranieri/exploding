package algebra

import cats.effect.Sync

trait ConsoleAlg {
  def putStrLn[F[_]: Sync](s: String): F[Unit]

  def readLn[F[_]: Sync]: F[String]
}
