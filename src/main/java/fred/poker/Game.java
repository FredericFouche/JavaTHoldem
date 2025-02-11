package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Game {
    private Deck deck;
    private List<Player> players;
    private List<Consumer<String>> subscribers;
    private Table table;
    private int MAX_PLAYERS = 8;
    private int MIN_PLAYERS = 2;


    public Game(int numberOfPlayers) {
        this.deck = new Deck();
        this.deck.shuffle();
        this.players = new ArrayList<>();
        this.subscribers = new ArrayList<>();

        this.table = new Table(deck);
        subscribe(table);

        if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be between 2 and 8.");
        } else {
            for (int i = 0; i < numberOfPlayers; i++) {
                Hand hand = new Hand(deck);
                Player player;
                if (i == 0) {
                    player = new Player("Player 1", false, hand);
                } else {
                    player = new Player("Player 2", true, hand);
                }
                players.add(player);
                subscribe(player);
            }
        }
    }

    public void dealCards() {
        notifySub("DEAL_CARDS");
    }

    public void dealFlop() {
        notifySub("DEAL_FLOP");
    }

    public void dealTurn() {
        notifySub("DEAL_TURN");
    }

    public void dealRiver() {
        notifySub("DEAL_RIVER");
    }

    public void endGame() {
        notifySub("END_GAME");
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void subscribe(Consumer<String> subscriber) {
        subscribers.add(subscriber);
    }

    private void notifySub(String event) {
        for (Consumer<String> subscriber : subscribers) {
            subscriber.accept(event);
        }
    }

    public Table getTable() {
        return table;
    }
}
