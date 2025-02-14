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
        Table table = new Table(deck, new EventManager());
        hand = new Hand(deck, table);
    }

    @Test
    void getPlayerHand() {
        assertNotNull(hand);
        assertEquals(0, hand.getPlayerHand().size());
    }

    @Test
    void drawCardPlayerHand() {
        hand.drawCardPlayerHand((byte) 2);
        assertEquals(2, hand.getPlayerHand().size());
    }

    @Test
    void drawCardPlayerHandIllegal() {
        hand.drawCardPlayerHand((byte) 2);
        assertThrows(IllegalArgumentException.class, () -> hand.drawCardPlayerHand((byte) 1));
    }

    @Test
    void clearPlayerHand() {
        hand.drawCardPlayerHand((byte) 2);
        hand.clearPlayerHand();
        assertEquals(0, hand.getPlayerHand().size());
    }

    @Test
    void getCompleteHandWithNoCards() {
        List<Card> finalHand = hand.getCompleteHand();
        assertEquals(0, finalHand.size());
    }

    @Test
    void getCompleteHandWithHoleCardsOnly() {
        hand.drawCardPlayerHand((byte) 2);
        List<Card> finalHand = hand.getCompleteHand();
        assertEquals(2, finalHand.size());
    }

    @Test
    void getCompleteHandWithCommunityCardsOnly() {
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getCompleteHand();
        assertEquals(5, finalHand.size());
    }

    @Test
    void getCompleteHandWithHoleAndCommunityCards() {
        hand.drawCardPlayerHand((byte) 2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getCompleteHand();
        assertEquals(7, finalHand.size());
    }

    @Test
    void encodeCompleteHandWithNoCards() {
        List<Card> finalHand = hand.getCompleteHand();
        int[] encodedHand = Hand.encodeCompleteHand(finalHand);
        assertEquals(0, encodedHand.length);
    }

    @Test
    void encodeCompleteHandWithHoleAndCommunityCards() {
        hand.drawCardPlayerHand((byte) 2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getCompleteHand();
        int[] encodedHand = Hand.encodeCompleteHand(finalHand);
        assertEquals(7, encodedHand.length);
    }

    @Test
    void findBestHandInRange() {
        Lookup.getInstance();

        // Construire le jeu de test
        hand.drawCardPlayerHand((byte) 2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getCompleteHand();

        int[] bestHand = hand.findBestHandInRange(finalHand);
        assertNotNull(bestHand);
    }
}