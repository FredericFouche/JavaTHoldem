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


}