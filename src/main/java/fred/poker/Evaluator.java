package fred.poker;

import java.util.Arrays;

public class Evaluator {
    private static Table table;
    private Hand hand;
    int[] values = new int[5];
    int[] suits = new int[5];


    public Evaluator(Table table, Hand hand) {
        Evaluator.table = table;
        this.hand = hand;
    }

    public int evaluateHand(int[] hand) {
        for (int i = 0; i < 5; i++) {
            values[i] = (hand[i] % 13 == 0) ? 13 : hand[i] % 13;
            suits[i] = (hand[i] - 1) / 13;
        }

        int uniqueKeyPrimeProduct = getPrimeProduct();

        return 1;
    }

    public int getPrimeProduct() {
        int[] primes = new int[5];
        for (int i = 0; i < values.length; i++) {
            primes[i] = Card.PRIMES[values[i]];
        }

        return Arrays.stream(primes).reduce(1, (a, b) -> a * b);
    }

    /*
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
                counts[value - 1]++;
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
                counts[value - 1]++;
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
    */
}
