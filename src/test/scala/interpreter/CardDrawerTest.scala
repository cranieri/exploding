package interpreter

import domain.{ Blank, Deck, DrawExit, Explosive }
import org.scalatest.{ FunSpec, Matchers }

class CardDrawerTest extends FunSpec with Matchers {

  describe("draw") {
    describe("the deck contains cards") {
      it("draws a card and returns the drawn card and a new deck") {
        CardDrawer.draw(Deck(List(Explosive, Blank))) shouldBe Right(Explosive, Deck(List(Blank)))
      }
    }

    describe("the deck is empty") {
      it("returns a DrawExit error") {
        CardDrawer.draw(Deck(List())) shouldBe Left(DrawExit)
      }
    }
  }
}
