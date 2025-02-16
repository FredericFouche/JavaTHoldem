package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static fred.poker.Table.deck;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand;
    Card card1;
    Card card2;
    Card card3;
    Table table;

    @BeforeEach
    void setUp() {
        Deck deck = new Deck();
        hand = new Hand(deck);
        table = new Table(deck, new EventManager());

        this.card1 = deck.draw();
        this.card2 = deck.draw();
        this.card3 = deck.draw();
    }

    @Test
    void getPlayerHand() {
        assertNotNull(hand);
        assertEquals(0, hand.getPlayerHand().size());
    }

    @Test
    void addCardToPlayerHand() {
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
        assertEquals(2, hand.getPlayerHand().size());
    }

    @Test
    void addCardToPlayerHandIllegal() {
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
        assertThrows(IllegalArgumentException.class, () -> hand.addCardToPlayerHand(card3));
    }

    @Test
    void clearPlayerHand() {
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
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
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
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
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
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
        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
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

        hand.addCardToPlayerHand(card1);
        hand.addCardToPlayerHand(card2);
        Table.dealFlop();
        Table.dealTurn();
        Table.dealRiver();
        List<Card> finalHand = hand.getCompleteHand();

        int[] bestHand = Hand.findBestHandInRange(finalHand, false);
        System.out.println("Best hand: " + Arrays.toString(bestHand));
        assertNotNull(bestHand);
    }

    @Test
    void randomHand() {
        int[] res = Hand.randomHand(deck);
        assertNotNull(res);
        assertEquals(5, res.length);
        System.out.println("Random hand: " + Arrays.toString(res));
    }
}
