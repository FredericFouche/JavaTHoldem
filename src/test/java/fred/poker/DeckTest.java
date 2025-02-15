package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void getDeck() {
        List<Card> fullDeck = (List<Card>) Deck.getDeck();
        assertNotNull(fullDeck);
        assertEquals(52, fullDeck.size());
        Set<Card> uniqueCards = new HashSet<>(fullDeck);
        assertEquals(52, uniqueCards.size());
    }

    @Test
    void draw() {
        Deck deck = new Deck();
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Card drawnCard = deck.draw();
            drawnCards.add(drawnCard);
        }
        assertNotNull(drawnCards);
        assertEquals(4, drawnCards.size());
        assertEquals(48, Deck.deck.size());
    }


    @Test
    void shuffle() {
        List<Card> originalDeck = new ArrayList<>(Deck.deck);
        deck.shuffle();
        List<Card> shuffledDeck = Deck.deck;

        // Vérifier que le deck a été mélangé
        assertNotEquals(originalDeck, shuffledDeck);

        // Afficher les valeurs des cartes dans les listes
        System.out.println("Original deck:");
        for (Card card : originalDeck) {
            System.out.println(card.getCard());
        }

        System.out.println("Shuffled deck:");
        for (Card card : shuffledDeck) {
            System.out.println(card.getCard());
        }

        // Vérifier que le deck contient toujours les mêmes cartes
        originalDeck.sort(Comparator.comparing(Card::toString));
        shuffledDeck.sort(Comparator.comparing(Card::toString));
        assertEquals(originalDeck, shuffledDeck);
    }

}