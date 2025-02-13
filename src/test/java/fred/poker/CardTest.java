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
    void getCard() {
        card = new Card((byte) 1, "DIAMONDS");
        assertEquals("1 DIAMONDS", card.getCard());
    }

    @Test
    void convertToCactusKev() {
        card = new Card((byte) 1, "DIAMONDS");
        assertEquals(131331, Card.convertToCactusKev(card));
    }


}