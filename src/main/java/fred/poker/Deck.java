package fred.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    List<Card> deck;

    public Deck() {
        this.deck = getFullDeck();
    }

    public List<Card> getFullDeck() {
        List<Card> fullDeck = new ArrayList<>();

        return fullDeck;
    }

    /**
     * Tire une carte du deck et la renvoie
     * @return int
     */
    public Card draw() {
        if (deck.isEmpty()) {
            throw new IllegalArgumentException("Deck is empty");
        }
        Card i = deck.remove(0).getCard();
        System.out.println(i);
        return i;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

}
