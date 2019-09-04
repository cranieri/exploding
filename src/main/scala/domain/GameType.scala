package domain

trait GameType {
  val deck: Deck
  val playerCard: Option[Card]
}

case object BasicRouletteRuse extends GameType {
  val cards = util.Random.shuffle(List.fill(46)(Blank) :+ Explosive)
  val deck: Deck = Deck(cards)
  val playerCard: Option[Card] = None
}
case object DefuseCards extends GameType {
  val cards = util.Random.shuffle((List.fill(46)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive)
  val deck: Deck = Deck(cards)
  val playerCard: Option[Card] = Some(Defuse)
}
