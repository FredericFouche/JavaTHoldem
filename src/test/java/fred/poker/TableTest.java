package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    List<Card> communityCards;
    Deck deck;
    Table table;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        table = new Table(deck);
    }

    @Test
    void dealFlop() {
        table.dealFlop();
        assertEquals(3, table.communityCards.size());
        assertNotNull(table.communityCards.get(0));
        assertNotNull(table.communityCards.get(1));
        assertNotNull(table.communityCards.get(2));
    }

    @Test
    void dealTurn() {
        table.dealFlop();
        table.dealTurn();
        assertEquals(4, table.communityCards.size());
        assertNotNull(table.communityCards.get(3));
    }

    @Test
    void dealRiver() {
        table.dealFlop();
        table.dealTurn();
        table.dealRiver();
        assertEquals(5, table.communityCards.size());
        assertNotNull(table.communityCards.get(4));
    }

    @Test
    void addPlayer() {
    }

    @Test
    void removePlayer() {
    }
}