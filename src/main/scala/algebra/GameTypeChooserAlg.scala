package algebra

import cats.data.EitherT
import cats.effect.{Effect, Sync}
import domain.{GameError, GameType}

trait GameTypeChooserAlg {
  def choose[F[_]: Sync]: EitherT[F, GameError, GameType]
}
