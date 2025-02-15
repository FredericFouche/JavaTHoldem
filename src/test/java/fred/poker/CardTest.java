package fred.poker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        assertEquals(card, card.getCard());
        System.out.println(card);
    }

    @Test
    void encodeTo32bitsInt() {
        card = new Card((byte) 1, "DIAMONDS");
        assertEquals(1179907, Card.encodeTo32bitsInt(card));
    }

    @Test
    void straightFlush() {
        Lookup.getInstance();

        Card c1 = new Card((byte) 1, "DIAMONDS");
        Card c2 = new Card((byte) 2, "DIAMONDS");
        Card c3 = new Card((byte) 3, "DIAMONDS");
        Card c4 = new Card((byte) 4, "DIAMONDS");
        Card c5 = new Card((byte) 5, "DIAMONDS");

        List<Card> cards = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
        int[] hand = Hand.encodeCompleteHand(cards);
        System.out.println(Arrays.toString(hand) + " hand straight flush test");

        int eval = Evaluator.evaluateHand(hand);
        System.out.println(eval);
    }

    @Test
    void testFullHouse() {
        Lookup.getInstance();
        Card c1 = new Card((byte) 0, "DIAMONDS");
        Card c2 = new Card((byte) 0, "CLUBS");
        Card c3 = new Card((byte) 0, "HEARTS");
        Card c4 = new Card((byte) 3, "SPADES");
        Card c5 = new Card((byte) 3, "DIAMONDS");

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);

        int[] hand = Hand.encodeCompleteHand(cards);
        System.out.println("Encoded hand for Full House: " + Arrays.toString(hand));

        int eval = Evaluator.evaluateHand(hand);

        System.out.println("Rank pour Full House : " + eval);
        Assertions.assertTrue(eval > Lookup.MAX_FOUR_OF_A_KIND && eval <= Lookup.MAX_FULL_HOUSE,
                "Le rang pour un Full House devrait être dans la plage correspondante.");
    }

    @Test
    void testStraightNoFlush() {
        Lookup.getInstance();

        Card c1 = new Card((byte) 0, "DIAMONDS");
        Card c2 = new Card((byte) 1, "CLUBS");
        Card c3 = new Card((byte) 2, "HEARTS");
        Card c4 = new Card((byte) 3, "SPADES");
        Card c5 = new Card((byte) 4, "DIAMONDS");

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);

        int[] hand = Hand.encodeCompleteHand(cards);
        System.out.println("Encoded hand for Straight (2..6): " + Arrays.toString(hand));

        int eval = Evaluator.evaluateHand(hand);
        System.out.println("Rank pour Straight 2..6 : " + eval);

        Assertions.assertTrue(eval > Lookup.MAX_FLUSH && eval <= Lookup.MAX_STRAIGHT,
                "Le rang pour un Straight devrait être dans la plage correspondante.");
    }


}