package interpreter

import cats.data.EitherT
import cats.effect.Sync
import domain._

object DefuseCardsCardChecker extends CardChecker {
  override def check[F[_]: Sync](
      card: Card,
      playerCard: Option[Defuse.type],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Defuse.type])] =
    card match {
      case _: Explosive.type =>
        playerCard match {
          case Some(_) =>
            for {
              _ <- EitherT.right(
                    Sync[F].delay(
                      println(
                        s"You drew an Explosive card but you have a Defuse. Carry on playing!"
                      )
                    )
                  )
              m <- EitherT[F, GameExit, (Deck, Option[Defuse.type])](
                    Sync[F].pure(Right(Deck(util.Random.shuffle(deck.cards :+ card)), None))
                  )
            } yield m
          case None =>
            for {
              _ <- EitherT.right(Sync[F].delay(println(s"Sorry, no more Defuses. You exploded!")))
              m <- EitherT[F, GameExit, (Deck, Option[Defuse.type])](Sync[F].pure(Left(Quit)))
            } yield m
        }
      case _: Defuse.type =>
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"You drew a Defuse. Carry on playing!")))
          m <- EitherT[F, GameExit, (Deck, Option[Defuse.type])](
                Sync[F].pure(Right(deck, playerCard))
              )
        } yield m
      case _: Blank.type =>
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"Turn finished")))
          m <- EitherT[F, GameExit, (Deck, Option[Defuse.type])](Sync[F].pure(Left(Quit)))
        } yield m
    }
}
