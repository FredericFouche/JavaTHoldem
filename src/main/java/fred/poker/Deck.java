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

        for (byte value : Card.VALUES) {
            for (String type : Card.TYPES) {
                fullDeck.add(new Card(value, type));
            }
        }

        return fullDeck;
    }

    public List<Card> draw(byte number) {
        if (number > deck.size()) {
            throw new IllegalArgumentException("Insufficient deck in deck to draw.");
        } else {
            List<Card> drawnDeck = new ArrayList<>(deck.subList(0,number));
            deck = new ArrayList<>(deck.subList(number, deck.size()));
            return drawnDeck;
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

}
