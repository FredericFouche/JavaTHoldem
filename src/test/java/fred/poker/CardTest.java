package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    Card card;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCardValue() {
        card = new Card((byte) 1, "DIAMONDS");
        assertEquals(1, card.getCardValue());
    }

    @Test
    void getCardType() {
        card = new Card((byte) 1, "DIAMONDS");
        assertEquals("DIAMONDS", card.getCardType());
    }

    @Test
    void getCard() {
        card = new Card((byte) 1, "DIAMONDS");
        assertEquals("1 DIAMONDS", card.getCard());
    }

    @Test
    void negativeCardValue() {
        card = new Card((byte) -1, "DIAMONDS");
        assertThrows(IllegalArgumentException.class, () -> card.getCardValue());
    }

    @Test
    void cardMaxRange() {
        card = new Card((byte) 127, "DIAMONDS");
        assertThrows(IllegalArgumentException.class, () -> card.getCardValue());
    }


    @Test
    void cardValueLimited() {
        card = new Card((byte) 1, "CHAT");
        assertThrows(IllegalArgumentException.class, () -> card.getCardType());
    }

    @Test
    void cardValueCaseSensitive() {
        card = new Card((byte) 1, "Diamonds");
        assertThrows(IllegalArgumentException.class, () -> card.getCardType());
    }


}