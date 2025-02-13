package fred.poker;

import java.util.ArrayList;
import java.util.Arrays;
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
                hand.add(drawnHand);
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

    public static int[] encodeFinalHandToCactusKev(List<Card> finalHand) {
        int[] encodedHand = new int[finalHand.size()];
        for (int i = 0; i < finalHand.size(); i++) {
            encodedHand[i] = Card.convertToCactusKev(finalHand.get(i));
        }
        return encodedHand;
    }

    /**
     * Evalue la meilleure main possible parmi les 21 mains possibles
     * @param finalHand : main finale
     * @return : tableau de 5 entiers représentant la meilleure main
     */
    public int[] evalBestHandWithinRange(List<Card> finalHand) {
        // le tableau pour stocker la meilleure main
        int[] bestHand = new int[5];
        // récupère la main avec 7 cartes et l'encode en Cactus Kev pour l'évaluer
        int[] encodedHand = encodeFinalHandToCactusKev(finalHand);
        System.out.println("encodedHand within evalBestHandWithinRange: " + Arrays.toString(encodedHand));
        // initialise le rang de la meilleure main à 0
        int bestRank = 7463;
        // initialise le rang de la main à 0
        int rank;

        // On génère toutes les combinaisons possibles de 5 cartes parmi les 7 cartes
        List<Integer> handLookup = Lookup.generateAllCombinations(7, 5);

        // Évaluer chaque combinaison
        for (int combination : handLookup) {
            int[] currentHand = new int[5];
            int index = 0;
            for (int i = 0; i < 7; i++) {
                if ((combination & (1 << i)) != 0) {
                    currentHand[index++] = encodedHand[i];
                }
            }

            System.out.println("currentHand in loop: " + Arrays.toString(currentHand));

            rank = Evaluator.evaluateHand(currentHand);

            System.out.println("rank in loop: " + rank);

            if (rank < bestRank) {
                bestRank = rank;
                System.out.println("bestRank in loop: " + bestRank);
                System.arraycopy(currentHand, 0, bestHand, 0, 5);
            }
        }

        // on retourne la meilleure main de 5 cartes
        return bestHand;
    }

    @Override
    public void accept(String s) {
        if (s.equals("DEAL_CARDS")) {
            addCardToHoleCards((byte) 2);
        }
    }

}
