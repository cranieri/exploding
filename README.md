## Explosive
The code in this repo implements the "explosive" game by following the principles of the Tagless Final design pattern, which is one of the most common functional programming design pattern used in Scala. 
      
### Running the application
* Execute the following command to start the application via sbt:
```
sbt run
```

* Then choose the type of game you want to play, after the following message is shown:
```
Press '1' to play Basic Roulette Ruse, '2' to play Defuse Cards (q to exit):
```
### Libraries used
- Cats (https://typelevel.org/cats/)

### Areas of improvements
- Add unit tests
- Add support for multiple players and ake application configurable (cards in a deck, number of players etc)
