import cats.effect.{ ExitCode, IO, IOApp }
import interpreter.{ CardDrawer, GameTypeChooser, PureConsole, Randomizer }
import program.Game

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val play = for {
      t <- new GameTypeChooser[IO](new PureConsole[IO](), Randomizer).choose
      p <- new Game[IO](t, new PureConsole[IO](), CardDrawer, Randomizer).play
    } yield p

    play.value.map {
      case Right(_) => ExitCode.Success
      case _        => ExitCode(0)
    }
  }
}
