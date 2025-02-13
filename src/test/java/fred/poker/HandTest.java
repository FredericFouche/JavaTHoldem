package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand;


    @BeforeEach
    void setUp() {
        Deck deck = new Deck();
        Table table = new Table(deck, new EventManager());
        hand = new Hand(deck, table);
    }

    @Test
    void getHoleCards() {
        assertNotNull(hand);
        assertEquals(0, hand.getHoleCards().size());
    }

    @Test
    void addCardTogetHoleCards() {
        hand.addCardToHoleCards((byte) 2);
        hand.addCardToHoleCards((byte) 1);
        assertEquals(2, hand.getHoleCards().size());
    }

    @Test
    void addCardTogetHoleCardsIllegal() {
        hand.addCardToHoleCards((byte) 2);
        assertThrows(IllegalArgumentException.class, () -> hand.addCardToHoleCards((byte) 1));
    }

    @Test
    void removeCardFromgetHoleCards() {
        hand.addCardToHoleCards((byte) 2);
        hand.removeCardFromHoleCards();
        assertEquals(0, hand.getHoleCards().size());
    }
}