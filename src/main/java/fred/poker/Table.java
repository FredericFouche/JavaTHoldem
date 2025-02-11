package fred.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Table implements Consumer<String> {
    List<Card> communityCards;
    Deck deck;

    public Table(Deck deck) {
        this.communityCards = new ArrayList<>();
        this.deck = deck;
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

    @Override
    public void accept(String s) {
        switch (s) {
            case "DEAL_FLOP":
                dealFlop();
                break;
            case "DEAL_TURN":
                dealTurn();
                break;
            case "DEAL_RIVER":
                dealRiver();
                break;
            case "END_GAME":
                endGame();
                break;
            default:
                throw new IllegalArgumentException("Unknown event");
        }
    }
}

