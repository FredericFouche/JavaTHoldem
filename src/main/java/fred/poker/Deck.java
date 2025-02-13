package fred.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    List<Card> deck;

    public Deck() {
        this.deck = createDeck();
        if (deck.size() != 52) {
            throw new IllegalStateException("Deck should have 52 cards but has " + deck.size());
        }
    }

    public List<Card> getFullDeck() {
        return new ArrayList<>(deck);
    }

    /**
     * Tire une carte du deck et la renvoie
     * @return int
     */
    public Card draw() {
        if (deck.isEmpty()) {
            throw new IllegalArgumentException("Deck is empty");
        }

        Card i = deck.remove(0);
        System.out.println(i);
        return i;
    }


    /**
     * Crée un deck de 52 cartes depuis les valeurs et couleurs des cartes
     * les valeurs et couleurs sont présentes dans la class Card
     * static int[] VALUES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
     * static String[] SUITS = {"DIAMONDS", "SPADES", "CLUBS", "HEARTS"};
     * @return Deck
     */
    private List<Card> createDeck() {
        List<Card> newDeck = new ArrayList<>();
        for (int value : Card.VALUES) {
            for (String suit : Card.SUITS) {
                newDeck.add(new Card(value, suit));
            }
        }
        return newDeck;
    }


    public void shuffle() {
        Collections.shuffle(deck);
    }

}
