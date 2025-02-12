package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Hand implements Consumer<String> {
    private final List<Card> hand;
    private Deck deck;

    public Hand(Deck deck) {
        this.hand = new ArrayList<>();
        this.deck = deck;
    }


    public List<Card> getHand() {
        if(hand.isEmpty()) {
            return hand;
        } else if (hand.size() == 2) {
            return hand;
        } else {
            throw new IllegalArgumentException("Hand can contain 0 or 2 cards.");
        }
    }

    /**
     * Encode la main en un tableau d'entiers
     * Permet de représenter une main par un entier
     *
     * @return int representation de la main
     */
    public int[] getEncodedHand() {
        int[] encodedHand = new int[2];
        for (int i = 0; i < hand.size(); i++) {
            encodedHand[i] = hand.get(i).encodeCardValue();
        }
        return encodedHand;
    }

    public void addCardToHand(byte number) {
        if (hand.size() < 2) {
            List<Card> drawnHand = deck.draw(number);
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

    @Override
    public void accept(String s) {
        if (s.equals("DEAL_CARDS")) {
            addCardToHand((byte) 2);
        }
    }
}
