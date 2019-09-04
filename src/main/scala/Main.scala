import cats.effect.{ExitCode, IO, IOApp}
import domain.{Blank, Card, Deck, Defuse, Explosive}
import interpreter.Game

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    val playerCard: Option[Card] = Some(Defuse)

    val cards = util.Random.shuffle((List.fill(5)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive)

    val deck = Deck(cards)

    Game.play[IO](deck, playerCard).value.map {
      case Right(_) => ExitCode.Success
      case _ => ExitCode(0)
    }
  }
}





