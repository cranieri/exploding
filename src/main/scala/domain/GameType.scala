package domain

trait GameType {
  val deck: Deck
  val playerCard: Option[Card]
}

case class BasicRouletteRuse(
    cards: List[Card] = util.Random.shuffle(List.fill(46)(Blank) :+ Explosive)
) extends GameType {
  val deck: Deck               = Deck(cards)
  val playerCard: Option[Card] = None
}
case class DefuseCards(
    cards: List[Card] =
      util.Random.shuffle((List.fill(46)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive)
) extends GameType {
  val deck: Deck               = Deck(cards)
  val playerCard: Option[Card] = Some(Defuse)
}
