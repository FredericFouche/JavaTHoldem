package fred.poker;

import java.util.function.Consumer;

public class Player implements Consumer<EventManager.EventType> {
    private String name;
    private boolean isAi;
    private final Hand hand;

    public Player(String name, boolean isAi, Hand hand, EventManager eventManager) {
        this.name = name;
        this.isAi = isAi;
        this.hand = hand;
        eventManager.subscribe(EventManager.EventType.DEAL_CARDS, this);
    }

    @Override
    public void accept(EventManager.EventType eventType) {
        if (eventType == EventManager.EventType.DEAL_CARDS) {
            handleDealCards();
        }
    }

    public String getName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty.");
        } else {
            return name;
        }
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        } else if (name.length() > 20) {
            throw new IllegalArgumentException("Name cannot be longer than 20 characters.");
        } else {
            this.name = name;
        }
    }

    public void setIsAi(boolean isAi) {
        this.isAi = isAi;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean getIsAi() {
        return isAi;
    }

    public void handleDealCards() {
        hand.addCardToHoleCards((byte) 2);
    }
}
