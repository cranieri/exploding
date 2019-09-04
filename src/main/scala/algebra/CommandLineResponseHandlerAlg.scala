package algebra

import domain.{GameError, GameType}

trait CommandLineResponseHandlerAlg {
  def handleResponse(r: String): Either[GameError, String]
  def handleResponseGameType(r: String): Either[GameError, GameType]
}
