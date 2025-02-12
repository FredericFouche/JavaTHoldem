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
            values[i] = (hand[i] % 13 == 0) ? 13 : hand[i] % 13;
            // Utilisation de la division entière pour déchiffrer la couleur de la carte ex : 14 / 13 = 1 => Pique
            suits[i] = (hand[i] - 1) / 13;
        }

        Arrays.sort(values);

        System.out.println("values: " + Arrays.toString(values));
        System.out.println("suits: " + Arrays.toString(suits));

        if (isStraightFlushRoyal(values, suits)) {
            return 10;
        } else if (isStraightFlush(values, suits)) {
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
            System.out.println("isOnePair" + Arrays.toString(values));
            return 2;
        } else {
            System.out.println("isHighCard" + Arrays.toString(values) + Arrays.toString(suits));
            return 1;
        }

    }

    static boolean isOnePair(int[] values) {
        return Arrays.stream(values).distinct().count() == 4;
    }

    static boolean isTwoPair(int[] values) {
        int[] counts = new int[13];
        for (int value : values) {
            counts[value - 1]++;
        }
        int pairCount = 0;
        for (int count : counts) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    static boolean isThreeOfAKind(int[] values) {
        int[] counts = new int[13];
        for (int value : values) {
            counts[value - 1]++;
        }
        for (int count : counts) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    static boolean isStraight(int[] values) {
        Arrays.sort(values);

        int intervalCount = 1;
        for (int i = 0; i < (values.length - 1) ; i++) {
            if ((values[i + 1] - values[i]) == 1) {
                intervalCount++;
                if (intervalCount == 5) {
                    return true;
                }
            } else {
                intervalCount = 1;
            }
        }
        return false;
    }

    static boolean isFlush(int[] suits) {
        return Arrays.stream(suits).distinct().count() == 1;
    }

    static boolean isFullHouse(int[] values) {
        Arrays.sort(values);

        int[] counts = new int[13];
        if (Arrays.stream(values).distinct().count() == 2) {
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
        return false;
    }

    static boolean isFourOfAKind(int[] values) {
        int[] counts = new int[13];
        if (Arrays.stream(values).distinct().count() == 2) {
            for (int value : values) {
                counts[value]++;
            }
            for (int count : counts) {
                if (count == 4) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    static boolean isStraightFlush(int[] values, int[] suits) {
        return isStraight(values) && isFlush(suits);
    }

    static boolean isStraightFlushRoyal(int[] values, int[] suits) {
        if (values[4] == 12 && values[0] == 0) {
            values[0] = 13;
            Arrays.sort(values);
        }
        return (isStraight(values) && isFlush(suits) && values[0] == 9);
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
