import cats.data.EitherT
import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    val playerCard: Option[Card.Value] = Some(Card.Defuse)

    val cards = util.Random.shuffle((List.fill(5)(Card.Blank) ::: List.fill(2)(Card.Defuse)) :+ Card.Explosive)

    val deck = Deck(cards)

    Player.playGame(deck, playerCard).value.map{
      case Right(_) => ExitCode.Success
      case _ => ExitCode(0)
    }
  }

}

object Player {
  def playGame(deck: Deck, playerCard: Option[Card.Value]): EitherT[IO, GameError, (Card.Value, Deck)] = {
    def play(deck: Deck, playerCard: Option[Card.Value]): EitherT[IO, GameError, (Card.Value, Deck)] = {
      val game = Game.play(deck, playerCard)

      EitherT(game.value.flatMap { x =>
        x match  {
          case Right((c, d)) => {
            println(s"good: $c")
            play(c,d).value
          }
          case Left(e) => {
            println("bad")
            IO(Left(Exploded))
          }
        }
      })
    }

    play(deck, playerCard)
  }
}

object CommandLineResponseHandler {
  def handleResponse(r: String): Either[GameError, String] = {
    if (r == "d") {
      Right(r)
    } else  {
      Left(Quit)
    }
  }
}

object Game {
  def play(deck: Deck, playerCard: Option[Card.Value]): EitherT[IO, GameError, (Deck, Option[Card.Value])] = {
    for {
      _ <- EitherT.right(IO(println("Press 'd' to draw a card (q to exit): ")))
      n <- EitherT.right(IO(scala.io.StdIn.readLine))
      _ <- EitherT(IO.pure(CommandLineResponseHandler.handleResponse(n)))
      c <- EitherT(IO.pure(CardDrawer.draw(deck)))
      d <- EitherT(IO.pure(CardChecker.check(c._1, playerCard, c._2)))
    } yield d
  }
}

object Card extends Enumeration {
  val Blank, Explosive, Defuse = Value
}

case class Deck(cards: List[Card.Value])

object CardDrawer {
  def draw(deck: Deck): Either[GameError, (Card.Value, Deck)] = {
    println(deck.cards)
    deck.cards match {
      case h :: t => Right(h, Deck(t))
      case _ => Left(DrawError)
    }
  }
}

object CardChecker {
  def check(card: Card.Value, playerCard: Option[Card.Value], deck: Deck): Either[GameError, (Deck, Option[Card.Value])] = {
    println(card)
    card match {
      case e if e.equals(Card.Explosive) => {
        playerCard match {
          case Some(c) if c == Card.Defuse => Right(Deck(util.Random.shuffle(deck.cards :+ card)), None)
          case _ => Left(Exploded)
        }
      }
      case _ => Right(deck, playerCard)
    }
  }
}

sealed trait GameError

case object Exploded extends GameError

case object DrawError extends GameError

case object Quit extends GameError
