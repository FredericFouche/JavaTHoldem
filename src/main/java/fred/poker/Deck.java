package fred.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    // Un deck est composé de 52 objets cartes
    static List<Card> deck;

    /*
     * Crée un deck de 52 cartes en utilisant la méthode create(), puis vérifie que le deck a bien 52 cartes.
     */
    public Deck() {
        this.deck = create();
        if (deck.size() != 52) {
            throw new IllegalStateException("Deck should have 52 cards but has " + deck.size());
        }
    }

    /**
     * Getter retourne le deck
     *
     * @return List<Card>
     */
    public static List<Card> getDeck() {
        return deck;
    }

    /**
     * Tire une carte du deck et la renvoie
     *
     * @return int
     */
    public Card draw() {
        if (deck.isEmpty()) {
            throw new IllegalArgumentException("Deck is empty");
        }
        return deck.remove(0);
    }


    /**
     * Crée un deck de 52 cartes depuis les valeurs et couleurs des cartes
     * @return List<Card> deck
     */
    private List<Card> create() {
        List<Card> newDeck = new ArrayList<>();
        for (int value : Card.VALUES) {
            for (String suit : Card.SUITS) {
                newDeck.add(new Card(value, suit));
            }
        }
        return newDeck;
    }


    /**
     * Mélange le deck en utilisant les méthodes static de la classe Collections
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void destroyInstance() {
        deck.clear();
    }

}
