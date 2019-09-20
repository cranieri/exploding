package algebra

import domain.Deck

trait RandomizerAlg {
  def run(d: Deck): Deck
}
