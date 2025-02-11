package fred.poker;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    // Un Deck a 52 cartes uniques
    // Créer une List pour stocker les cartes du Deck
    List<Card> cards;

    public Deck() {
        this.cards = getFullDeck();
    }

    public List<Card> getFullDeck() {
        List<Card> fullDeck = new ArrayList<>();

        // Une card est (1, "RED", "DIAMONDS)
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

}
