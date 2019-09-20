package interpreter

import algebra.{ CommandLineResponseHandlerAlg, ConsoleAlg, RandomizerAlg }
import cats.effect.Sync
import domain.{ GameExit, Quit }

class CommandLineResponseHandler[F[_]: Sync](console: ConsoleAlg[F], randomizer: RandomizerAlg)
    extends CommandLineResponseHandlerAlg[F] {
  def handleResponse(r: String): Either[GameExit, String] =
    r match {
      case "d" => Right(r)
      case _   => Left(Quit)
    }
}
