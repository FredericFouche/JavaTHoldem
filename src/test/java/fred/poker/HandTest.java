package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getHand() {
        hand = new Hand();
        assertNotNull(hand);
        assertEquals(0, hand.hand.size());
    }

    @Test
    void addCardToHand() {
        hand = new Hand();
        hand.addCardToHand();
        assertEquals(1, hand.hand.size());
        hand.addCardToHand();
        assertEquals(2, hand.hand.size());
    }

    @Test
    void addCardToHandIllegal() {
        hand = new Hand();
        hand.addCardToHand();
        hand.addCardToHand();
        assertThrows(IllegalArgumentException.class, () -> hand.addCardToHand());
    }

    @Test
    void removeCardFromHand() {
        hand = new Hand();
        hand.addCardToHand();
        hand.addCardToHand();
        hand.removeCardFromHand();
        assertEquals(0, hand.hand.size());
    }
}