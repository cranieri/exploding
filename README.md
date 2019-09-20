## Explosive
The code in this repo implements the "explosive" game by following the principles of the Tagless Final design pattern, which is one of the most common functional programming design pattern used in Scala. 
The idea behind the design of the application is to keep the core pure and execute side effect in the outer layer of the application, "at the end of the world".
      
### Running the application
* Execute the following command to start the application via sbt:
```
sbt run
```

* Then choose the type of game you want to play, after the following message is shown:
```
Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit):
```

### Rules for the `Basic roulette ruse` game
- There are one player and 47 cards. One of the cards is explosive and the rest are blank.
- All the cards are shuffled and arranged face down in a draw pile.
- The player draws cards one after the other. If the card is blank, it has no effect, and can be discarded. If the card is explosive, the player loses.

### Rules for the `Defuse Cards` game

* We have three additional _Defuse_ cards, making a total of 50 in the deck.
* Game set up:
  1. Give one defuse card to the player.
  2. Put the remaining two defuse cards with the rest in the draw pile, shuffle and arrange face down.
* The player's turn consists of two steps:
   1. Draw one card
   2. There are three alternatives:
      * Blank card: the turn finishes.
      * Explosive card, if the player has a defuse card: 
        1. Discard the defuse card onto the discard pile.
        2. Return the explosive card to the draw pile.
        3. Re-shuffle the draw pile.
      * Explosive card, if the player does not have a defuse card: The player loses.

### Tests
- Test have been written with scalatest (http://www.scalatest.org/) and can be run with following commad:
```
sbt test
```

### Libraries used
- Cats (https://typelevel.org/cats/)

### Areas of improvements
- Add support for multiple players and make application configurable (cards in a deck, number of players etc)
