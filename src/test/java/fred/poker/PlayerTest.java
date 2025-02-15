package fred.poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeEach
    void setUp() {
        Lookup.getInstance();
    }

    @Test
    void monteCarloSimulation() {
        Lookup.getInstance();

        int nbOftrials = 200;
        int handValue = 5326;

        int winrate = Player.monteCarloSimulation(nbOftrials, handValue);
        assertTrue(winrate >= 0 && winrate <= 100);


        System.out.println("-------------------");
        System.out.println("Win rate: " + winrate + "%");
        System.out.println("-------------------");
    }

    @Test
    void testMakeDecisionAiWithCompleteHand() {
        for (int i = 0; i < 50; i++) {
            EventManager eventManager = new EventManager();
            Deck deck = new Deck();
            deck.shuffle();
            Table table = new Table(deck, eventManager);

            eventManager.notifySubscribers(EventManager.EventType.DEAL_FLOP);
            eventManager.notifySubscribers(EventManager.EventType.DEAL_TURN);
            eventManager.notifySubscribers(EventManager.EventType.DEAL_RIVER);

            List<Card> holeCards = new ArrayList<>();
            holeCards.add(deck.draw());
            holeCards.add(deck.draw());

            Hand hand = new Hand(deck, table);
            hand.setHoleCards(holeCards);

            Player player = new Player("Player 1", true, hand, eventManager);
            player.makeDecisionAi();
        }
    }
}