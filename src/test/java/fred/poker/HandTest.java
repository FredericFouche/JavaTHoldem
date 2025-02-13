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
    void getHoleCards() {
        assertNotNull(hand);
        assertEquals(0, hand.getHoleCards().size());
    }

    @Test
    void addCardToHoleCards() {
        hand.addCardToHoleCards((byte) 2);
        assertEquals(2, hand.getHoleCards().size());
    }

    @Test
    void addCardToHoleCardsIllegal() {
        hand.addCardToHoleCards((byte) 2);
        assertThrows(IllegalArgumentException.class, () -> hand.addCardToHoleCards((byte) 1));
    }

    @Test
    void removeCardFromHoleCards() {
        hand.addCardToHoleCards((byte) 2);
        hand.removeCardFromHoleCards();
        assertEquals(0, hand.getHoleCards().size());
    }

    @Test
    void getFinalHandWithNoCards() {
        List<Card> finalHand = hand.getFinalHand();
        assertEquals(0, finalHand.size());
    }

    @Test
    void getFinalHandWithHoleCardsOnly() {
        hand.addCardToHoleCards((byte) 2);
        List<Card> finalHand = hand.getFinalHand();
        assertEquals(2, finalHand.size());
    }

    @Test
    void getFinalHandWithCommunityCardsOnly() {
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getFinalHand();
        assertEquals(5, finalHand.size());
    }

    @Test
    void getFinalHandWithHoleAndCommunityCards() {
        hand.addCardToHoleCards((byte) 2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getFinalHand();
        assertEquals(7, finalHand.size());
    }

    @Test
    void encodeFinalHandToCactusKevWithNoCards() {
        List<Card> finalHand = hand.getFinalHand();
        int[] encodedHand = Hand.encodeFinalHandToCactusKev(finalHand);
        assertEquals(0, encodedHand.length);
    }

    @Test
    void encodeFinalHandToCactusKevWithHoleAndCommunityCards() {
        hand.addCardToHoleCards((byte) 2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getFinalHand();
        int[] encodedHand = Hand.encodeFinalHandToCactusKev(finalHand);
        assertEquals(7, encodedHand.length);
    }

    @Test
    void evalBestHandWithinRange() {
        Lookup.getInstance();

        // Construire le jeu de test
        hand.addCardToHoleCards((byte) 2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getFinalHand();

        int[] bestHand = hand.evalBestHandWithinRange(finalHand);
        assertNotNull(bestHand);
    }
}