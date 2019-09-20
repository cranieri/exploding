package interpreter

import cats.effect.IO
import org.scalatest.{ FunSpec, Matchers }

class PureConsoleTest extends FunSpec with Matchers {
  describe("putStrLn") {
    it("returns and effect to print a sentence to the console") {
      val c = new PureConsole[IO]("sdin")
      c.putStrLn("hello").unsafeRunSync() shouldBe println("hello")
    }
  }

  describe("readLn") {
    it("returns and effect to read characters from the console") {
      val c = new PureConsole[IO]("sdin")
      c.readLn.unsafeRunSync() shouldBe "sdin"
    }
  }
}
