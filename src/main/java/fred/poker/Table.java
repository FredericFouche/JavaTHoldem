package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Table implements Consumer<EventManager.EventType> {
    List<Card> communityCards;
    Deck deck;
    private enum HandleEvent {
        DEAL_FLOP,
        DEAL_TURN,
        DEAL_RIVER,
        END_GAME
    }

    public Table(Deck deck, EventManager eventManager) {
        this.communityCards = new ArrayList<>();
        this.deck = deck;
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

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public void dealFlop() {
        if (!communityCards.isEmpty()) {
            throw new IllegalArgumentException("Flop has already been drawn");
        } else {
            List<Card> flopDrawn = deck.draw((byte) 3);
            communityCards.addAll(flopDrawn);
        }
    }

    public void dealTurn() {
        if (communityCards.size() == 3) {
            List<Card> turnDrawn = deck.draw((byte) 1);
            communityCards.addAll(turnDrawn);
        } else {
            throw new IllegalArgumentException("Flop has not been drawn yet");
        }
    }

    public void dealRiver() {
        if (communityCards.size() == 4) {
            List<Card> riverDrawn = deck.draw((byte) 1);
            communityCards.addAll(riverDrawn);
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

