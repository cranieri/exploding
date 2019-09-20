package domain

import algebra.{ ConsoleAlg, RandomizerAlg }
import cats.data.EitherT
import cats.effect.Sync
import interpreter.Randomizer

trait GameType[F[_]] {
  val playerCard: Option[Card]
  val deck: Deck

  def check(
      card: Card,
      playerCard: Option[Card],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Card])]
}

case class BasicRouletteRuse[F[_]: Sync](
    console: ConsoleAlg[F],
    deck: Deck,
    playerCard: Option[Card]
) extends GameType[F] {

  def check(
      card: Card,
      playerCard: Option[Card],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Card])] =
    card match {
      case _: Explosive.type =>
        for {
          _ <- EitherT.right(console.putStrLn(s"Sorry! You drew a $card card. You exploded!"))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Exploded)))
        } yield m
      case _ =>
        for {
          _ <- EitherT.right(
                Sync[F].delay(console.putStrLn(s"Well done! You drew a $card card. Keep drawing"))
              )
          m <- EitherT[F, GameExit, (Deck, Option[Card])](
                Sync[F].pure(Right(deck, playerCard))
              )
        } yield m
    }
}

case class DefuseCards[F[_]: Sync](
    deck: Deck,
    playerCard: Option[Card],
    console: ConsoleAlg[F],
    randomizer: RandomizerAlg = Randomizer
) extends GameType[F] {

  def check(
      card: Card,
      playerCard: Option[Card],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Card])] =
    card match {
      case _: Explosive.type =>
        playerCard match {
          case Some(_) =>
            for {
              _ <- EitherT.right(
                    console.putStrLn(
                      s"You drew an Explosive card but you have a Defuse. Carry on playing!"
                    )
                  )
              m <- EitherT[F, GameExit, (Deck, Option[Card])](
                    Sync[F].pure(Right(randomizer.run(Deck(deck.cards :+ card)), None))
                  )
            } yield m
          case None =>
            for {
              _ <- EitherT.right(console.putStrLn(s"Sorry, no more Defuses. You exploded!"))
              m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Quit)))
            } yield m
        }
      case _: Defuse.type =>
        for {
          _ <- EitherT.right(console.putStrLn(s"You drew a Defuse. Carry on playing!"))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](
                Sync[F].pure(Right(deck, playerCard))
              )
        } yield m
      case _: Blank.type =>
        for {
          _ <- EitherT.right(console.putStrLn(s"Turn finished"))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Quit)))
        } yield m
    }
}
