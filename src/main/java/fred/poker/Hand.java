package fred.poker;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> hand;
    Deck deck;

    public Hand() {
        this.hand = new ArrayList<>();
        this.deck = new Deck();
    }

    public List<Card> getHand() {
        // Une main n'a que 2 cartes maximums et 0 carte minimum
        if(hand.isEmpty()) {
            return hand;
        } else if (hand.size() == 2) {
            return hand;
        } else {
            throw new IllegalArgumentException("Hand can contain 0 or 2 cards.");
        }
    }

    public void addCardToHand() {
        // appel de la méthode draw() depuis la class Deck pour obtenir 2 cartes
        if (hand.size() < 2) {
            List<Card> drawnHand = deck.draw((byte) 1);
            hand.addAll(drawnHand);
        } else {
            throw new IllegalArgumentException("Hand is limited to 2 cards.");
        }
    }

    public void removeCardFromHand() {
        if(hand.isEmpty()) {
            throw new IllegalArgumentException("There is no card in hand.");
        }
        hand.clear();
    }

}
