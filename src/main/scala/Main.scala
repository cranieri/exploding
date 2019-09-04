import cats.effect.{ExitCode, IO, IOApp}
import domain.{Card, Deck}
import interpreter.Game

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    val playerCard: Option[Card.Value] = Some(Card.Defuse)

    val cards = util.Random.shuffle((List.fill(5)(Card.Blank) ::: List.fill(2)(Card.Defuse)) :+ Card.Explosive)

    val deck = Deck(cards)

    Game.play[IO](deck, playerCard).value.map {
      case Right(_) => ExitCode.Success
      case _ => ExitCode(0)
    }
  }
}





