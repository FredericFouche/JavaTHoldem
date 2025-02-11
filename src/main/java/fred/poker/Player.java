package fred.poker;

import java.util.function.Consumer;

public class Player implements Consumer<String> {
    private String name;
    private boolean isAi;
    private final Hand hand;

    public Player(String name, boolean isAi, Hand hand) {
        this.name = name;
        this.isAi = isAi;
        this.hand = hand;
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

    // Permet de recevoir les événements envoyés par les classes abonnées
    @Override
    public void accept(String s) {
        if (s.equals("DEAL_CARDS")) {
            hand.addCardToHand((byte) 2);
        }
    }

    // Permet de s'abonner à une classe
    public void subscribe(Game game) {
        game.subscribe(this);
    }
}
