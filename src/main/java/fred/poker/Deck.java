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

        byte[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ,13};
        String[] types = {"DIAMONDS", "SPADES", "CLUBS", "HEARTS"};

        for (String type : types) {
            for (byte value : values) {
                if (type.contains("DIAMONDS") || type.contains("HEARTS")) {
                    fullDeck.add(new Card (value, "RED", type));
                } else {
                    fullDeck.add(new Card (value, "BLACK", type));
                }
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
