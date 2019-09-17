import cats.effect.{ ExitCode, IO, IOApp }
import interpreter.{ CardChecker, CardDrawer, GameTypeChooser, PureConsole }
import program.Game

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val play = for {
      t <- GameTypeChooser.choose[IO]
      p <- new Game(t, PureConsole, CardDrawer, CardChecker[IO](t)).play[IO]
    } yield p

    play.value.map {
      case Right(_) => ExitCode.Success
      case _        => ExitCode(0)
    }
  }
}
