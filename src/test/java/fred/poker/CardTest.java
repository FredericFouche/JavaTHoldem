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
        card = new Card((byte) 1, "RED", "DIAMONDS");
        assertEquals(1, card.getCardValue());
    }

    @Test
    void getCardColor() {
        card = new Card((byte) 1, "RED", "DIAMONDS");
        assertEquals("RED", card.getCardColor());
    }

    @Test
    void getCardType() {
        card = new Card((byte) 1, "RED", "DIAMONDS");
        assertEquals("DIAMONDS", card.getCardType());
    }

    @Test
    void getCard() {
        card = new Card((byte) 1, "RED", "DIAMONDS");
        assertEquals("1 RED DIAMONDS", card.getCard());
    }

    @Test
    void negativeCardValue() {
        card = new Card((byte) -1, "RED", "DIAMONDS");
        assertThrows(IllegalArgumentException.class, () -> card.getCardValue());
    }

    @Test
    void cardMaxRange() {
        card = new Card((byte) 127, "RED", "DIAMONDS");
        assertThrows(IllegalArgumentException.class, () -> card.getCardValue());
    }

    @Test
    void cardColorBLACK() {
        card = new Card((byte) 1, "BLACK", "DIAMONDS");
        assertEquals("BLACK", card.getCardColor());
    }

    @Test
    void cardColorCase() {
        card = new Card((byte) 1, "Red", "DIAMONDS");
        assertThrows(IllegalArgumentException.class, () -> card.getCardColor());
    }

    @Test
    void cardColorLimited() {
        card = new Card((byte) 1, "BLUE", "DIAMONDS");
        assertThrows(IllegalArgumentException.class, () -> card.getCardColor());
    }

    @Test
    void cardValueLimited() {
        card = new Card((byte) 1, "BLACK", "CHAT");
        assertThrows(IllegalArgumentException.class, () -> card.getCardType());
    }

    @Test
    void cardValueCaseSensitive() {
        card = new Card((byte) 1, "BLACK", "Diamonds");
        assertThrows(IllegalArgumentException.class, () -> card.getCardType());
    }


}