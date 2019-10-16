package interpreter

import algebra.{ CommandLineResponseHandlerAlg, ConsoleAlg }
import cats.effect.Sync
import domain.{ GameExit, Quit }

class CommandLineResponseHandler[F[_]: Sync](console: ConsoleAlg[F])
    extends CommandLineResponseHandlerAlg[F] {
  def handleResponse(r: String): Either[GameExit, String] =
    r match {
      case "d" => Right(r)
      case _   => Left(Quit)
    }
}
