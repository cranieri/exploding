package algebra

import domain.{ GameExit }

trait CommandLineResponseHandlerAlg[F[_]] {
  def handleResponse(r: String): Either[GameExit, String]
}
