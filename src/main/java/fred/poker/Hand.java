package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Hand implements Consumer<String> {
    private final List<Card> hand;
    private final Deck deck;
    private final Table table; // permet de récupérer les cartes de la table
    private List<Card> finalHand;

    public Hand(Deck deck, Table table) {
        this.hand = new ArrayList<>();
        this.finalHand = new ArrayList<>();
        this.deck = deck;
        this.table = table;
    }


    public List<Card> getHoleCards() {
        if(hand.isEmpty()) {
            return hand;
        } else if (hand.size() == 2) {
            return hand;
        } else {
            throw new IllegalArgumentException("Hand can contain 0 or 2 cards.");
        }
    }

    public void addCardToHoleCards(byte number) {
        if (hand.size() < 2) {
            for (int i = 0; i < number; i++) {
                Card drawnHand = deck.draw();
            }
        } else {
            throw new IllegalArgumentException("Hand is limited to 2 cards.");
        }
    }

    public void removeCardFromHoleCards() {
        if(hand.isEmpty()) {
            throw new IllegalArgumentException("There is no card in hand.");
        }
        hand.clear();
    }

    /**
     * Crée la main finale en ajoutant les cartes de la table et les cartes cachées
     * @return : List des 7 cartes (valeurs/couleurs) de la main finale
     */
    public List<Card> getFinalHand() {
        List<Card> finalHand = new ArrayList<>();
        finalHand.addAll(getHoleCards());
        finalHand.addAll(table.getCommunityCards());
        return finalHand;
    }

    public int[] encodeFinalHandToCactusKev(List<Card> finalHand) {
        int[] encodedHand = new int[finalHand.size()];

        for (int i = 0; i < finalHand.size(); i++) {
            encodedHand[i] = Card.convertToCactusKev(finalHand.get(i));
        }
        return encodedHand;
    }

    @Override
    public void accept(String s) {
        if (s.equals("DEAL_CARDS")) {
            addCardToHoleCards((byte) 2);
        }
    }

}
