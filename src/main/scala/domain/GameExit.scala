package domain

sealed trait GameExit

case object Exploded extends GameExit

case object DrawExit extends GameExit

case object Quit extends GameExit
