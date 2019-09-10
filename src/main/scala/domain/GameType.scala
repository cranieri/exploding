package domain

trait GameType {
  val deck: Deck
  val playerCard: Option[Defuse.type]
}

case object BasicRouletteRuse extends GameType {
  val cards                           = util.Random.shuffle(List.fill(46)(Blank) :+ Explosive)
  val deck: Deck                      = Deck(cards)
  val playerCard: Option[Defuse.type] = None
}
case object DefuseCards extends GameType {
  val cards                           = util.Random.shuffle((List.fill(46)(Blank) ::: List.fill(2)(Defuse)) :+ Explosive)
  val deck: Deck                      = Deck(cards)
  val playerCard: Option[Defuse.type] = Some(Defuse)
}
