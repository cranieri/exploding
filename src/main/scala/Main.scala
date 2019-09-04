import cats.effect.{ExitCode, IO, IOApp}
import interpreter.GameTypeChooser
import program.Game

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val play = for {
      t <- GameTypeChooser.choose[IO]
      p <- Game.play[IO](t)
    } yield p

    play.value.map {
      case Right(_) => ExitCode.Success
      case _ => ExitCode(0)
    }
  }
}





