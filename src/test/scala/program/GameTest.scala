package program

import algebra.ConsoleAlg
import cats.Id
import cats.effect.{Effect, IO, Sync}
import domain.Quit
import org.scalatest.Matchers
//import cats.effect.{Effect, IO, Sync}
import cats.implicits._
import domain.BasicRouletteRuse
import interpreter.PureConsole
import org.scalatest.FunSpec
import org.scalamock.scalatest.MockFactory

class GameTest extends FunSpec with MockFactory with Matchers {
  describe("Game#play") {
    it("allows drawing cards until the user presses a key different than 'd'") {

      val g = Game.play[IO](BasicRouletteRuse, ConsoleMock)

      val res = g.value.unsafeRunSync()

      res shouldBe Left(Quit)
    }
  }
}


object ConsoleMock extends ConsoleAlg {
  var counter = 1

  def putStrLn[F[_] : Effect](s: String): F[Unit] = Sync[F].delay(println(s))

  def readLn[F[_] : Effect]: F[String] = {
    if (counter == 1) {
      counter = counter + 1
      Sync[F].delay("d")
    } else Sync[F].delay("q")
  }
}

