package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Table implements Consumer<EventManager.EventType> {
    static List<Card> communityCards;
    static Deck deck;
    private enum HandleEvent {
        DEAL_FLOP,
        DEAL_TURN,
        DEAL_RIVER,
        END_GAME
    }
    public Table(Deck deck, EventManager eventManager) {
        communityCards = new ArrayList<>();
        Table.deck = deck;
        for (HandleEvent event : HandleEvent.values()) {
            eventManager.subscribe(EventManager.EventType.valueOf(event.toString()), this);
        }
    }

    @Override
    public void accept(EventManager.EventType eventType) {
        if (eventType == EventManager.EventType.DEAL_FLOP) {
            dealFlop();
        } else if (eventType == EventManager.EventType.DEAL_TURN) {
            dealTurn();
        } else if (eventType == EventManager.EventType.DEAL_RIVER) {
            dealRiver();
        } else if (eventType == EventManager.EventType.END_GAME) {
            endGame();
        } else {
            throw new IllegalArgumentException("Invalid event type");
        }
    }

    public static List<Card> getCommunityCards() {
        return communityCards;
    }

    public static void dealFlop() {
        if (!communityCards.isEmpty()) {
            throw new IllegalArgumentException("Flop has already been drawn");
        } else {
            for (int i = 0; i < 3; i++) {
                Card flopDrawn = deck.draw();
                communityCards.add(flopDrawn);
            }
        }
    }

    public static void dealTurn() {
        if (communityCards.size() == 3) {
            Card flopDrawn = deck.draw();
            communityCards.add(flopDrawn);
        } else {
            throw new IllegalArgumentException("Flop has not been drawn yet");
        }
    }

    public static void dealRiver() {
        if (communityCards.size() == 4) {
            Card flopDrawn = deck.draw();
            communityCards.add(flopDrawn);
        } else {
            throw new IllegalArgumentException("Turn has not been drawn yet");
        }
    }

    public void wipeTable() {
        communityCards.clear();
    }

    public void endGame() {
        wipeTable();
        System.out.println("Game ended");
    }
}

