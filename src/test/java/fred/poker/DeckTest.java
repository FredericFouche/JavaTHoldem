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
    void getFullDeck() {
        List<Card> fullDeck = deck.getFullDeck();
        assertNotNull(fullDeck);
        assertEquals(52, fullDeck.size());
        Set<Card> uniqueCards = new HashSet<>(fullDeck);
        assertEquals(52, uniqueCards.size());
    }

    @Test
    void draw() {
        // Tirer 5 cartes avec la méthode draw
        Card drawnDeck = deck.draw();
        // Vérifier que le deck a 47 cartes restantes et que les 5 cartes tirées sont bien dans la main
        assertNotNull(drawnDeck);
        assertEquals(47, deck.deck.size());
    }

    @Test
    void shuffle() {
        List<Card> originalDeck = new ArrayList<>(deck.deck);
        deck.shuffle();
        List<Card> shuffledDeck = deck.deck;

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