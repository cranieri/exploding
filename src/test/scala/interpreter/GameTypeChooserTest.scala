package interpreter

import cats.effect.{ IO, Sync }
import domain.{ BasicRouletteRuse, Blank, Deck, Defuse, DefuseCards, Explosive }
import org.scalamock.scalatest.MockFactory
import org.scalatest.{ FunSpec, Matchers }

class GameTypeChooserTest extends FunSpec with MockFactory with Matchers {
  private val console = mock[MockableConsole]

  class MockableConsole(implicit val io: Sync[IO]) extends PureConsole("readLn")

  describe("choose") {

    describe("when 1 is typed by the users") {
      it("reads the user input and return a BasicRouletteRuse game type") {
        console.putStrLn _ expects ("Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit): ") returns IO(
          println("Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit): ")
        ) once ()
        val gtc = new GameTypeChooser[IO](console, RandomizerTest).choose

        console.readLn _ expects () returns IO("1") anyNumberOfTimes ()
        gtc.value.unsafeRunSync() shouldBe Right(
          BasicRouletteRuse(console, (Deck(List.fill(46)(Blank) :+ Explosive)), None)
        )
      }
    }

    describe("when 2 is typed by the users") {

      it("reads the user input and return a BasicRouletteRuse game type") {
        console.putStrLn _ expects ("Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit): ") returns IO(
          println("Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit): ")
        ) once ()
        val gtc = new GameTypeChooser[IO](console, RandomizerTest).choose

        console.readLn _ expects () returns IO("2") anyNumberOfTimes ()
        gtc.value.unsafeRunSync() shouldBe Right(
          DefuseCards(
            Deck((List.fill(46)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive),
            Some(Defuse),
            console
          )
        )
      }
    }
  }
}
