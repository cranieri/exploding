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

  def handleResponseGameType(r: String): Either[GameError, String] = {
    if (r == "1" || r == "2") {
      Right(r)
    } else {
      Left(Quit)
    }
  }
}
