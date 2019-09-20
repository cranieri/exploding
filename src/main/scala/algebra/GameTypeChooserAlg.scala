package algebra

import cats.data.EitherT
import domain.{ GameExit, GameType }

trait GameTypeChooserAlg[F[_]] {
  def choose: EitherT[F, GameExit, GameType[F]]
}
