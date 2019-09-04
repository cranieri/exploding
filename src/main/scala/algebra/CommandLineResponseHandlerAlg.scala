package algebra

import domain.GameError

trait CommandLineResponseHandlerAlg {
  def handleResponse(r: String): Either[GameError, String]
  def handleResponseGameType(r: String): Either[GameError, String]
}
