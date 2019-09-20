package interpreter

import algebra.RandomizerAlg
import domain.Deck

object Randomizer extends RandomizerAlg {
  def run(d: Deck): Deck = Deck(util.Random.shuffle(d.cards))
}
