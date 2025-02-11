package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand;


    @BeforeEach
    void setUp() {
        Deck deck = new Deck();
        hand = new Hand(deck);
    }

    @Test
    void getHand() {
        assertNotNull(hand);
        assertEquals(0, hand.getHand().size());
    }

    @Test
    void addCardToHand() {
        hand.addCardToHand((byte) 1);
        hand.addCardToHand((byte) 1);
        assertEquals(2, hand.getHand().size());
    }

    @Test
    void addCardToHandIllegal() {
        hand.addCardToHand((byte) 2);
        assertThrows(IllegalArgumentException.class, () -> hand.addCardToHand((byte) 1));
    }

    @Test
    void removeCardFromHand() {
        hand.addCardToHand((byte) 2);
        hand.removeCardFromHand();
        assertEquals(0, hand.getHand().size());
    }
}