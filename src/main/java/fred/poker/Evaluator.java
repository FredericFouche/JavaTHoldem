package fred.poker;

import java.util.Arrays;

public class Evaluator {
    private static Table table;
    private Hand hand;

    public Evaluator(Table table, Hand hand) {
        this.table = table;
        this.hand = hand;
    }
    public static int evaluateHand(int[] hand) {
        // Extraire les valeurs et les couleurs des cartes
        int[] values = new int[5];
        int[] suits = new int[5];

        for (int i = 0; i < 5; i++) {
            values[i] = hand[i] % 13; // Valeur de la carte (0-12)
            suits[i] = hand[i] / 13; // Couleur de la carte (0-3)
        }

        // Trier les valeurs pour faciliter l'analyse
        Arrays.sort(values);

        // Vérifier les types de mains
        if (isStraightFlush(values, suits)) {
            return 9; // Quinte flush
        } else if (isFourOfAKind(values)) {
            return 8; // Carré
        } else if (isFullHouse(values)) {
            return 7; // Full
        } else if (isFlush(suits)) {
            return 6; // Couleur
        } else if (isStraight(values)) {
            return 5; // Suite
        } else if (isThreeOfAKind(values)) {
            return 4; // Brelan
        } else if (isTwoPair(values)) {
            return 3; // Double paire
        } else if (isOnePair(values)) {
            return 2; // Paire
        } else {
            return 1; // Carte haute
        }
    }

    private static int findBestHand(int[] allCards) {
        // bestRank sert à stocker le meilleur rang de main trouvé
        int bestRank = 0;

        // On parcourt toutes les combinaisons possibles de 5 cartes parmi les 7
        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 7; j++) {
                for (int k = j + 1; k < 7; k++) {
                    for (int l = k + 1; l < 7; l++) {
                        for (int m = l + 1; m < 7; m++) {
                            int[] currentHand = {allCards[i], allCards[j], allCards[k], allCards[l], allCards[m]};
                            int currentRank = Lookup.lookupHand(currentHand);
                            if (currentRank > bestRank) {
                                bestRank = currentRank;
                            }
                        }
                    }
                }
            }
        }

        return bestRank;
    }


}
