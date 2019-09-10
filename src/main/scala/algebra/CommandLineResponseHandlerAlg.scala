package algebra

import domain.{ GameExit, GameType }

trait CommandLineResponseHandlerAlg {
  def handleResponse(r: String): Either[GameExit, String]
  def handleResponseGameType(r: String): Either[GameExit, GameType]
}
