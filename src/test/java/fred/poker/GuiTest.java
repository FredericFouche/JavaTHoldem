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
        gui.generateCard(card);
    }

}