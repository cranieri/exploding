package algebra

trait ConsoleAlg[F[_]] {
  def putStrLn(s: String): F[Unit]

  def readLn: F[String]
}
