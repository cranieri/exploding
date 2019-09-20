package interpreter

import algebra.{ RandomizerAlg }
import cats.effect.{ IO, Sync }
import domain.{ Deck, Quit }
import org.scalamock.scalatest.MockFactory
import org.scalatest.{ FunSpec, Matchers }

class CommandLineResponseHandlerTest extends FunSpec with Matchers with MockFactory {
  private val console = mock[MockableConsole]

  class MockableConsole(implicit val io: Sync[IO]) extends PureConsole("readLn")

  val clrh = new CommandLineResponseHandler(console, RandomizerTest)

  describe("handleResponse") {
    describe("when 'd' is passed") {
      it("returns a Right(d)") {
        clrh.handleResponse("d") shouldBe Right("d")
      }
    }

    describe("when a different character than 'd' is passed") {
      it("returns a Left(Quit)") {
        clrh.handleResponse("anything") shouldBe Left(Quit)
      }
    }
  }
}

object RandomizerTest extends RandomizerAlg {
  def run(d: Deck): Deck = d
}
