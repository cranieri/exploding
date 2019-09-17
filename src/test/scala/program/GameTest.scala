package program

import algebra.{ CardDrawerAlg, ConsoleAlg }
import cats.effect.{ IO, Sync }
import domain.{ BasicRouletteRuse, Blank, Deck, DefuseCards, DrawExit, Exploded, Explosive }
import interpreter.{ BasicRouletteRuseCardChecker, CardDrawer }
import org.scalamock.scalatest.MockFactory
import org.scalatest.{ FunSpec, Matchers }

class GameTest extends FunSpec with Matchers with MockFactory {

  private val cardDrawer = mock[CardDrawerAlg]

  describe("Basic roulette ruse") {
    describe("when an explosive card is drawn") {
      it("explodes and game exits") {
        val game = new Game(
          BasicRouletteRuse(List(Explosive)),
          ConsoleTest,
          CardDrawer,
          BasicRouletteRuseCardChecker
        )
        val g = game.play[IO]

        g.value.unsafeRunSync() shouldBe Left(Exploded)
      }
    }

    describe("when a blank card is drawn") {
      it("has no effects, the card is discarded") {

        cardDrawer.draw _ expects (Deck(List(Blank, Blank))) returns Right(
          Blank,
          Deck(List.fill(1)(Blank))
        ) once ()
        cardDrawer.draw _ expects (Deck(List(Blank))) returns Right(Blank, Deck(List())) once ()
        cardDrawer.draw _ expects (Deck(List())) returns Left(DrawExit) once ()
        val game = new Game(
          BasicRouletteRuse(List(Blank, Blank)),
          ConsoleTest,
          cardDrawer,
          BasicRouletteRuseCardChecker
        )
        game.play[IO].value.unsafeRunSync()
      }
    }
  }

  describe("Defuse cards") {
    describe("when an explosive card is drawn") {
      it("discards the player's defuse card") {
        val game = new Game(
          DefuseCards(List(Explosive)),
          ConsoleTest,
          CardDrawer,
          BasicRouletteRuseCardChecker
        )
        val g = game.play[IO]

        g.value.unsafeRunSync() shouldBe Left(Exploded)
      }
    }
  }
}

object ConsoleTest extends ConsoleAlg {
  def putStrLn[F[_]: Sync](s: String): F[Unit] =
    Sync[F].delay(println(s))

  def readLn[F[_]: Sync]: F[String] =
    Sync[F].delay("d")
}
