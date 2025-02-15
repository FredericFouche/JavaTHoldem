# JavaTHoldem

JavaTHoldem is a Texas Hold'em poker game written in Java.
It is a simple implementation of the game, using a console interface and ASCII art.
It is a project for myself to learn Java and to practice programming.

## Features

- Play Texas Hold'em poker against computer players
- Computer players have a simple AI that makes them play
- The game is played in the console with ASCII art
- The game is played with a deck of 52 cards
- The game is played with 2 to 8 players

---

## How to play

Soon

---

## How to run

Soon

---

## Technical details

I use Cactus Kev algorithm, translated to Java, to evaluate the strength of a hand.

---

### Cactus Kev algorithm

The Cactus Kev algorithm is an algorithm to evaluate the strength of a hand in poker.
It can be find [here](http://suffe.cool/poker/evaluator.html).

1. The first step is to represent each card as a 32-bit integer.
This integer is composed of 4 bit ranges to store attributes :

    ```
    Bit structure :
    [ Prime | Rank | Suit | Card ]
    ```
   | Bits  | Meaning                                    |
   |-------|--------------------------------------------|
   | 0-7   | Unique prime number per each rank          |
   | 8-11  | Rank (0 = 2, 12 = Ace)                     |
   | 12-15 | Suit (Clubs, Diamonds, Hearts, Spades)     |
   | 16-31 | Bitmask with a single bit set at 16 + rank |


2. The second step is to evaluate the Hand strength.
   - **_First_** : Check for a flush using a bitmask.
   - **_Second_** : For non-flush hands, we use a Prime Product Algorithm to evaluate the hand strength.
   - **_Third_** : Once we have either : Bitmask for flush, Prime product for non-flush, we can look up the hand strength in 2 different precomputed tables, one for flushes and one for non-flushes.

---
## To do

- [x] Implement the game methods
- [x] Implement the Cactus Kev algorithm
- [x] Implement Class for : Card, Deck, Hand, Player
- [x] Write tests for the classes
- [x] Clean up codebas
- [x] Refactor the code
- [ ] Comment code with Javadoc
- [ ] Game interface
- [ ] Game logic
- [ ] Implement the AI
- [ ] Readme
