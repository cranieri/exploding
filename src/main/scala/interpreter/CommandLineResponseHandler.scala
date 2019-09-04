package interpreter

import algebra.CommandLineResponseHandlerAlg
import domain.{GameError, Quit}

object CommandLineResponseHandler extends CommandLineResponseHandlerAlg {
  def handleResponse(r: String): Either[GameError, String] = {
    if (r == "d") {
      Right(r)
    } else {
      Left(Quit)
    }
  }
}
