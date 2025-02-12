package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Game {
    private final List<Player> players;
    private List<Consumer<String>> subscribers;
    private final Table table;
    private final EventManager eventManager = new EventManager();


    public Game(int numberOfPlayers) {
        Deck deck = new Deck();
        deck.shuffle();
        this.players = new ArrayList<>();
        this.subscribers = new ArrayList<>();

        this.table = new Table(deck, eventManager);

        int MAX_PLAYERS = 8;
        int MIN_PLAYERS = 2;
        if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be between 2 and 8.");
        } else {
            for (int i = 0; i < numberOfPlayers; i++) {
                Hand hand = new Hand(deck);
                Player player;
                if (i == 0) {
                    player = new Player("Player 1", false, hand, eventManager);
                } else {
                    player = new Player("Player 2", true, hand, eventManager);
                }
                players.add(player);
            }
        }
    }

    public void dealCards() {
        eventManager.notifySubscribers(EventManager.EventType.valueOf("DEAL_CARDS"));
    }

    public void dealFlop() {
        eventManager.notifySubscribers(EventManager.EventType.valueOf("DEAL_FLOP"));
    }

    public void dealTurn() {
        eventManager.notifySubscribers(EventManager.EventType.valueOf("DEAL_TURN"));
    }

    public void dealRiver() {
        eventManager.notifySubscribers(EventManager.EventType.valueOf("DEAL_RIVER"));
    }

    public void endGame() {
        eventManager.notifySubscribers(EventManager.EventType.valueOf("END_GAME"));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Table getTable() {
        return table;
    }
}
