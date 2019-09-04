package interpreter

import algebra.CommandLineResponseHandlerAlg
import domain.{BasicRouletteRuse, DefuseCards, GameError, GameType, Quit}

object CommandLineResponseHandler extends CommandLineResponseHandlerAlg {
  def handleResponse(r: String): Either[GameError, String] = {
    r match {
      case "d" => Right(r)
      case _ => Left(Quit)
    }
  }

  def handleResponseGameType(r: String): Either[GameError, GameType] = {
    r match {
      case "1" => Right(BasicRouletteRuse)
      case "2" => Right(DefuseCards)
      case _ => Left(Quit)
    }
  }
}
