package algebra

import cats.data.EitherT
import cats.effect.{Effect, Sync}
import domain.{GameExit, GameType}

trait GameTypeChooserAlg {
  def choose[F[_]: Sync]: EitherT[F, GameExit, GameType]
}
