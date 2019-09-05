package algebra

import cats.effect.Effect

trait ConsoleAlg {
  def putStrLn[F[_]: Effect](s:String)
  def readLn[F[_]: Effect]
}
