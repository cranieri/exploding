package interpreter

import algebra.CommandLineResponseHandlerAlg
import domain.{ BasicRouletteRuse, DefuseCards, GameExit, GameType, Quit }

object CommandLineResponseHandler extends CommandLineResponseHandlerAlg {
  def handleResponse(r: String): Either[GameExit, String] =
    r match {
      case "d" => Right(r)
      case _   => Left(Quit)
    }

  def handleResponseGameType(r: String): Either[GameExit, GameType] =
    r match {
      case "1" => Right(BasicRouletteRuse())
      case "2" => Right(DefuseCards())
      case _   => Left(Quit)
    }
}
