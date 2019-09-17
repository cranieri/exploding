package domain

import cats.data.EitherT
import cats.effect.Sync

sealed trait GameType {
  val deck: Deck
  val playerCard: Option[Card]

  def check[F[_]: Sync](
      card: Card,
      playerCard: Option[Card],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Card])]
}

case class BasicRouletteRuse(
    cards: List[Card] = util.Random.shuffle(List.fill(46)(Blank) :+ Explosive)
) extends GameType {
  val deck: Deck               = Deck(cards)
  val playerCard: Option[Card] = None

  def check[F[_]: Sync](
      card: Card,
      playerCard: Option[Card],
      deck: Deck
  ): EitherT[F, GameExit, (Deck, Option[Card])] =
    card match {
      case _: Explosive.type =>
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"Sorry! You drew a $card card. You exploded!")))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Exploded)))
        } yield m
      case _ =>
        for {
          _ <- EitherT.right(
                Sync[F].delay(println(s"Well done! You drew a $card card. Keep drawing"))
              )
          m <- EitherT[F, GameExit, (Deck, Option[Card])](
                Sync[F].pure(Right(deck, playerCard))
              )
        } yield m
    }
}

case class DefuseCards(
    cards: List[Card] =
      util.Random.shuffle((List.fill(46)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive)
) extends GameType {
  val deck: Deck               = Deck(cards)
  val playerCard: Option[Card] = Some(Defuse)

  def check[F[_]: Sync](
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
                    Sync[F].delay(
                      println(
                        s"You drew an Explosive card but you have a Defuse. Carry on playing!"
                      )
                    )
                  )
              m <- EitherT[F, GameExit, (Deck, Option[Card])](
                    Sync[F].pure(Right(Deck(util.Random.shuffle(deck.cards :+ card)), None))
                  )
            } yield m
          case None =>
            for {
              _ <- EitherT.right(Sync[F].delay(println(s"Sorry, no more Defuses. You exploded!")))
              m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Quit)))
            } yield m
        }
      case _: Defuse.type =>
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"You drew a Defuse. Carry on playing!")))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](
                Sync[F].pure(Right(deck, playerCard))
              )
        } yield m
      case _: Blank.type =>
        for {
          _ <- EitherT.right(Sync[F].delay(println(s"Turn finished")))
          m <- EitherT[F, GameExit, (Deck, Option[Card])](Sync[F].pure(Left(Quit)))
        } yield m
    }
}
