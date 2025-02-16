package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(2);
    }

    @Test
    void dealCards() {
        game.dealCards();
        for (Player player : game.getPlayers()) {
            assertEquals(2, player.getHand().getPlayerHand().size(), "Chaque joueur doit avoir 2 cartes en main.");
        }
    }

    @Test
    void getPlayers() {
        List<Player> players = game.getPlayers();
        assertEquals(2, players.size(), "Le nombre de joueurs doit être 2.");
        assertEquals("Player 1", players.get(0).getName(), "Le nom du premier joueur doit être 'Player 1'.");
        assertEquals("Player 2", players.get(1).getName(), "Le nom du deuxième joueur doit être 'Player 2'.");
        assertFalse(players.get(0).getIsAi(), "Le premier joueur doit être humain.");
        assertTrue(players.get(1).getIsAi(), "Le deuxième joueur doit être une IA.");
    }

    @Test
    void errorPlayerNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Game(1), "Le nombre de joueurs doit être entre 2 et 8.");
        assertThrows(IllegalArgumentException.class, () -> new Game(9), "Le nombre de joueurs doit être entre 2 et 8.");
    }

    @Test
    void dealFlop() {
        game.dealFlop();
        assertEquals(3, Table.getCommunityCards().size(), "Le flop doit contenir 3 cartes.");
    }

    @Test
    void dealTurn() {
        game.dealFlop();
        game.dealTurn();
        assertEquals(4, Table.getCommunityCards().size(), "Le turn doit contenir 4 cartes.");
    }

    @Test
    void dealRiver() {
        game.dealFlop();
        game.dealTurn();
        game.dealRiver();
        assertEquals(5, Table.getCommunityCards().size(), "Le river doit contenir 5 cartes.");
    }

    @Test
    void endGame() {
        game.endGame();
        assertEquals(0, Table.getCommunityCards().size(), "La table doit être vide.");
    }
}