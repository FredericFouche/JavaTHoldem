package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void getFullDeck() {
        List<Card> fullDeck = deck.getFullDeck();
        assertNotNull(fullDeck);
        assertEquals(52, fullDeck.size());
        Set<Card> uniqueCards = new HashSet<>(fullDeck);
        assertEquals(52, uniqueCards.size());
    }

}