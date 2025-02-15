package fred.poker;

import java.util.*;
import java.util.function.Consumer;

public class Hand implements Consumer<String> {
    /**
     * La main du joueur est une liste de Card
     */
    private final List<Card> hand;


    /**
     * Le deck de cartes de la partie
     */
    private final Deck deck;

    /**
     * La table de la partie
     */
    private final Table table;

    /**
     * Constructeur de la classe Hand
     * Crée une main vide
     * @param deck : le deck de cartes de la partie
     * @param table : la table de la partie
     */
    public Hand(Deck deck, Table table) {
        this.hand = new ArrayList<>();
        this.deck = deck;
        this.table = table;
    }

    /**
     * Retourne la main du joueur
     * @return : la main du joueur
     */
    public List<Card> getPlayerHand() {
        if (hand.isEmpty() || hand.size() == 2) return hand; else throw new IllegalArgumentException("Player hand can contain 0 or 2 cards.");
    }

    /**
     * Ajoute une carte tiré du deck à la main du joueur
     */
    public void drawCardPlayerHand(byte number) {
        if (hand.size() + number <= 2) {
            for (int i = 0; i < number; i++) {
                hand.add(deck.draw());
            }
        } else {
            throw new IllegalArgumentException("Hand is limited to 2 cards.");
        }
    }

    /**
     * Vide la main du joueur
     */
    public void clearPlayerHand() {
        if(hand.isEmpty()) {
            throw new IllegalArgumentException("There is no card in hand.");
        }
        hand.clear();
    }

    /**
     * Crée la main finale en ajoutant les cartes de la table et les cartes cachées
     * @return : List des 2-7 cartes (valeurs/couleurs) de la main finale
     */
    public List<Card> getCompleteHand() {
        List<Card> finalHand = new ArrayList<>();
        finalHand.addAll(getPlayerHand());
        finalHand.addAll(table.getCommunityCards());
        return finalHand;
    }

    /**
     * Encode la main finale en un tableau de 5 entiers
     * @param finalHand : main finale
     * @return : tableau de 5 entiers représentant la main finale
     */
    public static int[] encodeCompleteHand(List<Card> finalHand) {
        int[] encodedHand = new int[finalHand.size()];
        for (int i = 0; i < finalHand.size(); i++) {
            encodedHand[i] = Card.encodeTo32bitsInt(finalHand.get(i));
        }
        return encodedHand;
    }

    /**
     * Evalue la meilleure main possible parmi les 21 mains possibles
     * @param finalHand : main finale
     * @return : tableau de 5 entiers représentant la meilleure main
     */
    public static int[] findBestHandInRange(List<Card> finalHand, Boolean isAi) {
        int[] bestHand = new int[5];
        int[] encodedFinalHand = encodeCompleteHand(finalHand);
        if (!isAi) {
            isAi = false;
        }

        // initialise le rang de la meilleure main à 7463 (pire main possible)
        int bestRank = 7463;
        int rank;

        /*
         * Générer toutes les combinaisons possibles de 5 cartes parmi les 7 cartes de la main finale
         * Pour en faire une table pré-calculée
         */
        List<Integer> handLookup = Lookup.generateAllCombinations(7, 5);

        /*
         * Pour chaque combinaison de 5 cartes possible dans les 7 cartes, évalue la main et garde la meilleure
         */
        for (int combination : handLookup) {
            int[] currentHand = new int[5];
            int index = 0;
            for (int i = 0; i < 7; i++) {
                if ((combination & (1 << i)) != 0) {
                    currentHand[index++] = encodedFinalHand[i];
                }
            }

            rank = Evaluator.evaluateHand(currentHand);

            if (rank < bestRank) {
                bestRank = rank;
                System.arraycopy(currentHand, 0, bestHand, 0, 5);
            }
        }

        if (isAi) {
            int[] bestHandAndRank = new int[6];
            System.arraycopy(bestHand, 0, bestHandAndRank, 0, 5);
            bestHandAndRank[5] = bestRank;
            return bestHandAndRank;
        }

        return bestHand;
    }

    /**
     * Methode pour générer une main aléatoire utile pour l'IA
     * @return : tableau de 5 entiers représentant une main aléatoire
     */
    public static int[] randomHand(Deck actualDeck) {
        int[] randomHand = new int[5];
        for (int i = 0; i < 5; i++) {
            randomHand[i] = Card.encodeTo32bitsInt(actualDeck.draw());
        }
        return randomHand;
    }

    /**
     * Methode pour gérer l'abonnement à l'evennement DEAL_CARDS
     * @param s : l'event
     */
    @Override
    public void accept(String s) {
        if (s.equals("DEAL_CARDS")) {
            drawCardPlayerHand((byte) 2);
        }
    }

    public void setHoleCards(List<Card> holeCards) {
        if (holeCards.size() != 2) {
            throw new IllegalArgumentException("Hole cards must contain 2 cards.");
        }
        hand.clear();
        hand.addAll(holeCards);
    }
}
