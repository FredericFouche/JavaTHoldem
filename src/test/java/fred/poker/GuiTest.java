package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuiTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGenerateCard() {
        Card card = new Card(2, "HEARTS");
        Gui gui = new Gui();
        Hand hand = new Hand(new Deck());

        gui.displayHand(hand);
    }

    @Test
    void displayHand() {
        Card card1 = new Card(2, "HEARTS");
        Card card2 = new Card(3, "DIAMONDS");
        Hand hand = new Hand(new Deck());
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
        Gui gui = new Gui();
        gui.displayHand(hand);
    }
}