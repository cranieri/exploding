package program

import algebra.{ CardDrawerAlg, RandomizerAlg }
import cats.effect.{ IO, Sync }
import domain.{ BasicRouletteRuse, Blank, Deck, Defuse, DefuseCards, DrawExit, Exploded, Explosive }
import interpreter.{ CardDrawer, PureConsole }
import org.scalamock.scalatest.MockFactory
import org.scalatest.{ FunSpec, Matchers }

class GameTest extends FunSpec with Matchers with MockFactory {

  private val cardDrawer = mock[CardDrawerAlg]
  private val console    = mock[MockableConsole]

  class MockableConsole(implicit val io: Sync[IO]) extends PureConsole("readLn")

  describe("Basic roulette ruse") {
    describe("when an explosive card is drawn") {
      it("explodes and game exits") {
        console.putStrLn _ expects "Press 'd' to draw a card (any other key to exit): " returns IO(
          println("Press 'd' to draw a card (any other key to exit): ")
        ) once ()
        console.readLn _ expects () returns IO("d") once ()

        console.putStrLn _ expects "Sorry! You drew a Explosive card. You exploded!" returns IO(
          println("Sorry! You drew a Explosive card. You exploded!")
        ) once ()

        val game = new Game[IO](
          BasicRouletteRuse(console, Deck(List(Explosive)), None),
          console,
          CardDrawer,
          RandomizerTest
        )

        game.play.value.unsafeRunSync() shouldBe Left(Exploded)
      }
    }

    describe("when a blank card is drawn") {
      it("has no effects, the card is discarded") {

        cardDrawer.draw _ expects (Deck(List(Blank, Blank))) returns Right(
          Blank,
          Deck(List(Blank))
        ) once ()
        cardDrawer.draw _ expects (Deck(List(Blank))) returns Right(Blank, Deck(List())) once ()
        cardDrawer.draw _ expects (Deck(List())) returns Left(DrawExit) once ()

        console.putStrLn _ expects ("Press 'd' to draw a card (any other key to exit): ") returns IO(
          println("Press 'd' to draw a card (any other key to exit): ")
        ) anyNumberOfTimes ()
        console.readLn _ expects () returns IO("d") anyNumberOfTimes ()

        console.putStrLn _ expects "Well done! You drew a Blank card. Keep drawing" returns IO(
          println("Well done! You drew a Blank card. Keep drawing")
        ) anyNumberOfTimes ()

        val game = new Game[IO](
          BasicRouletteRuse(console, Deck(List(Blank, Blank)), None),
          console,
          cardDrawer,
          RandomizerTest
        )

        game.play.value.unsafeRunSync()
      }
    }
  }

  describe("Defuse cards") {
    describe("when an explosive card is drawn") {
      it("discards the player's defuse card") {
        cardDrawer.draw _ expects (Deck(List(Explosive, Blank))) returns Right(
          Explosive,
          Deck(List(Blank))
        ) once ()

        cardDrawer.draw _ expects (Deck(List(Blank, Explosive))) returns Right(
          Blank,
          Deck(List(Explosive))
        ) once ()

        console.putStrLn _ expects ("Press 'd' to draw a card (any other key to exit): ") returns IO(
          println("Press 'd' to draw a card (any other key to exit): ")
        ) anyNumberOfTimes ()
        console.readLn _ expects () returns IO("d") anyNumberOfTimes ()

        console.putStrLn _ expects "You drew an Explosive card but you have a Defuse. Carry on playing!" returns IO(
          println("You drew an Explosive card but you have a Defuse. Carry on playing!")
        ) once ()

        console.putStrLn _ expects "Turn finished" returns IO(
          println("Turn finished")
        ) once ()

        val game = new Game(
          DefuseCards(Deck(List(Explosive, Blank)), Some(Defuse), console, RandomizerTest),
          console,
          cardDrawer,
          RandomizerTest
        )

        game.play.value.unsafeRunSync()
      }
    }
  }
}

object RandomizerTest extends RandomizerAlg {
  def run(d: Deck): Deck = d
}
