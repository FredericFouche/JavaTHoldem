package fred.poker;

/**
 * This class is used to generate a lookup table for the evaluator.
 * This table will have every possible hand and its corresponding rank.
 */
public class Lookup {
    private static final int[] lookupTable = new int[2_598_960];

    static {
        initializeLookupTable();
    }

    /**
     * Initialize the lookup table with every possible hand and its corresponding rank.
     */
    private static void initializeLookupTable() {
        int index = 0;

        for (int i = 0; i < 52; i++) {
            for (int j = i + 1; j < 52; j++) {
                for (int k = j + 1; k < 52; k++) {
                    for (int l = k + 1; l < 52; l++) {
                        for (int m = l + 1; m < 52; m++) {
                            int[] hand = {i, j, k, l, m};
                            int rank = Evaluator.evaluateHand(hand);
                            int handValue = encodeHand(hand);
                            lookupTable[index++] = rank;
                        }
                    }
                }
            }
        }
    }

    public static int lookupHand(int[] currentHand) {
        int handValue = encodeHand(currentHand);
        return lookupTable[handValue];
    }

    public static int encodeHand(int[] currentHand) {
        int value = 0;
        for (int card : currentHand) {
            value = (value << 6) | card;
        }
        return value;
    }
}
