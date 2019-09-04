package domain

sealed trait GameError

case object Exploded extends GameError

case object DrawError extends GameError

case object Quit extends GameError
