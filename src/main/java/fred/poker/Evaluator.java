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
            // Utilisation du modulo pour déchiffrer la valeur de la carte ex : 14 % 13 = 1 => As
            values[i] = hand[i] % 13;
            // Utilisation de la division entière pour déchiffrer la couleur de la carte ex : 14 / 13 = 1 => Pique
            suits[i] = hand[i] / 13;
        }

        Arrays.sort(values);

        if (isStraightFlush(values, suits)) {
            return 9;
        } else if (isFourOfAKind(values)) {
            return 8;
        } else if (isFullHouse(values)) {
            return 7;
        } else if (isFlush(suits)) {
            return 6;
        } else if (isStraight(values)) {
            return 5;
        } else if (isThreeOfAKind(values)) {
            return 4;
        } else if (isTwoPair(values)) {
            return 3;
        } else if (isOnePair(values)) {
            return 2;
        } else {
            return 1;
        }
    }

    private static boolean isOnePair(int[] values) {
        return Arrays.stream(values).distinct().count() == 4;
    }

    private static boolean isTwoPair(int[] values) {
        int[] counts = new int[13];
        for (int value : values) {
            counts[value]++;
        }
        int pairCount = 0;
        for (int count : counts) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    private static boolean isThreeOfAKind(int[] values) {
        int[] counts = new int[13];
        for (int value : values) {
            counts[value]++;
        }
        for (int count : counts) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean isStraight(int[] values) {
        Arrays.sort(values);
        int intervalCount = 0;
        for (int i = 0; i < (values.length - 1) ; i++) {
            if ((values[i] - values[i + 1]) == -1) {
                intervalCount++;
            } else {
                break;
            }
            if (intervalCount == 4) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFlush(int[] suits) {
        return Arrays.stream(suits).distinct().count() == 1;
    }

    private static boolean isFullHouse(int[] values) {
    }

    private static boolean isFourOfAKind(int[] values) {
    }

    private static boolean isStraightFlush(int[] values, int[] suits) {
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
