/*package fred.poker;

import org.junit.jupiter.api.Test;

import static fred.poker.Evaluator.findBestHand;
import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    void isOnePair_returnsTrue_forOnePair() {
        int[] onePairHand = {1, 1, 2, 3, 4}; // One pair
        assertTrue(Evaluator.isOnePair(onePairHand));
    }

    @Test
    void isOnePair_returnsFalse_forNoPair() {
        int[] noPairHand = {1, 2, 3, 4, 5}; // No pair
        assertFalse(Evaluator.isOnePair(noPairHand));
    }

    @Test
    void isTwoPair_returnsTrue_forTwoPair() {
        int[] twoPairHand = {1, 1, 2, 2, 4}; // Two pairs
        assertTrue(Evaluator.isTwoPair(twoPairHand));
    }

    @Test
    void isTwoPair_returnsFalse_forOnePair() {
        int[] onePairHand = {1, 1, 2, 3, 4}; // One pair
        assertFalse(Evaluator.isTwoPair(onePairHand));
    }

    @Test
    void isThreeOfAKind_returnsTrue_forThreeOfAKind() {
        int[] threeOfAKindHand = {1, 1, 1, 2, 4}; // Three of a kind
        assertTrue(Evaluator.isThreeOfAKind(threeOfAKindHand));
    }

    @Test
    void isThreeOfAKind_returnsFalse_forTwoPair() {
        int[] twoPairHand = {1, 1, 2, 2, 4}; // Two pairs
        assertFalse(Evaluator.isThreeOfAKind(twoPairHand));
    }

    @Test
    void isStraight_returnsTrue_forStraight() {
        int[] straightHand = {0, 1, 2, 3, 4}; // Straight
        assertTrue(Evaluator.isStraight(straightHand));
    }

    @Test
    void isStraight_returnsFalse_forNonConsecutive() {
        int[] nonConsecutiveHand = {0, 2, 4, 6, 8}; // Non-consecutive
        assertFalse(Evaluator.isStraight(nonConsecutiveHand));
    }

    @Test
    void isFlush_returnsTrue_forFlush() {
        int[] flushHand = {1, 1, 1, 1, 1}; // All cards of the same suit
        assertTrue(Evaluator.isFlush(flushHand));
    }

    @Test
    void isFlush_returnsFalse_forMixedSuits() {
        int[] mixedSuitsHand = {0, 1, 1, 1, 2}; // Mixed suits
        assertFalse(Evaluator.isFlush(mixedSuitsHand));
    }

    @Test
    void isFullHouse_returnsTrue_forFullHouse() {
        int[] fullHouseHand = {1, 1, 1, 2, 2}; // Full house
        assertTrue(Evaluator.isFullHouse(fullHouseHand));
    }

    @Test
    void isFullHouse_returnsFalse_forThreeOfAKind() {
        int[] threeOfAKindHand = {1, 1, 1, 2, 3}; // Three of a kind
        assertFalse(Evaluator.isFullHouse(threeOfAKindHand));
    }

    @Test
    void isFourOfAKind_returnsTrue_forFourOfAKind() {
        int[] fourOfAKindHand = {1, 1, 1, 1, 2}; // Four of a kind
        assertTrue(Evaluator.isFourOfAKind(fourOfAKindHand));
    }

    @Test
    void isFourOfAKind_returnsFalse_forThreeOfAKind() {
        int[] threeOfAKindHand = {1, 1, 1, 2, 3}; // Three of a kind
        assertFalse(Evaluator.isFourOfAKind(threeOfAKindHand));
    }

    @Test
    void isStraightFlush_returnsTrue_forStraightFlush() {
        int[] straightFlushHand = {0, 1, 2, 3, 4}; // Straight flush
        assertTrue(Evaluator.isStraightFlush(straightFlushHand, new int[]{0, 0, 0, 0, 0}));
    }

    @Test
    void isStraightFlush_returnsFalse_forStraight() {
        int[] straightHand = {0, 1, 2, 3, 4}; // Straight
        assertFalse(Evaluator.isStraightFlush(straightHand, new int[]{0, 1, 2, 3, 4}));
    }

    @Test
    void isStraightFlushRoyal_returnsTrue_forRoyalFlush() {
        int[] royalFlushHand = {0, 9, 10, 11, 12}; // Royal flush
        assertTrue(Evaluator.isStraightFlushRoyal(royalFlushHand, new int[]{0, 0, 0, 0, 0}));
    }

    @Test
    void isStraightFlushRoyal_returnsFalse_forStraightFlush() {
        int[] straightFlushHand = {0, 1, 2, 3, 4}; // Straight flush
        assertFalse(Evaluator.isStraightFlushRoyal(straightFlushHand, new int[]{0, 0, 0, 0, 0}));
    }

    @Test
    void evaluateHand_returnsTen_forStraightFlushRoyal() {
        int[] royalFlushHand = {0, 9, 10, 11, 12};
        assertEquals(10, Evaluator.evaluateHand(royalFlushHand));
    }

    @Test
    void evaluateHand_returnsNine_forStraightFlush() {
        int[] straightFlushHand = {1, 2, 3, 4, 5};
        assertEquals(9, Evaluator.evaluateHand(straightFlushHand));
    }

    @Test
    void evaluateHand_returnsEight_forFourOfAKind() {
        int[] fourOfAKindHand = {1, 1, 1, 1, 2};
        assertEquals(8, Evaluator.evaluateHand(fourOfAKindHand));
    }

    @Test
    void evaluateHand_returnsSeven_forFullHouse() {
        int[] fullHouseHand = {1, 1, 1, 2, 2};
        assertEquals(7, Evaluator.evaluateHand(fullHouseHand));
    }

    @Test
    void evaluateHand_returnsSix_forFlush() {
        int[] flushHand = {1, 1, 1, 1, 1};
        assertEquals(6, Evaluator.evaluateHand(flushHand));
    }

    @Test
    void evaluateHand_returnsFive_forStraight() {
        int[] straightHand = {18, 6, 7, 8, 9};
        assertEquals(5, Evaluator.evaluateHand(straightHand));
    }

    @Test
    void evaluateHand_returnsFour_forThreeOfAKind() {
        int[] threeOfAKindHand = {0, 26, 39, 50, 51};
        assertEquals(4, Evaluator.evaluateHand(threeOfAKindHand));
    }

    @Test
    void evaluateHand_returnsThree_forTwoPair() {
        int[] twoPairHand = {0, 26, 5, 1, 27};
        assertEquals(3, Evaluator.evaluateHand(twoPairHand));
    }

    @Test
    void evaluateHand_returnsTwo_forOnePair() {
        int[] onePairHand = {0, 26, 5, 4, 27};
        assertEquals(2, Evaluator.evaluateHand(onePairHand));
    }

    @Test
    void evaluateHand_returnsOne_forHighCard() {
        int[] highCardHand = {0, 3, 6, 34, 50};
        assertEquals(1, Evaluator.evaluateHand(highCardHand));
    }

    @Test
    public void testFindBestHand() {
        System.out.println("findBestHand");
    }

}*/