package fred.poker;

import java.util.function.Consumer;

/**
 * Player class that represents a player or an AI in the game.
 */
public class Player implements Consumer<EventManager.EventType> {
    private String name;
    private boolean isAi;
    // TODO
    private final Hand hand;

    /**
     * Constructeur d'un joueur.
     * @param name Le nom du joueur.
     * @param isAi Si le joueur est un AI.
     * @param hand La main du joueur.
     * @param eventManager Le eventManager.
     */
    public Player(String name, boolean isAi, Hand hand, EventManager eventManager) {
        this.name = name;
        this.isAi = isAi;
        this.hand = hand;
        eventManager.subscribe(EventManager.EventType.DEAL_CARDS, this);
    }

    /**
     * Accepte un événement.
     * @param eventType Le type de l'événement.
     */
    @Override
    public void accept(EventManager.EventType eventType) {
        if (eventType == EventManager.EventType.DEAL_CARDS) {
            handleDealCards();
        }
    }

    // --- GETTERS & SETTERS ---

    /**
     * Retourne le nom du joueur.
     * @return Le nom du joueur.
     */
    public String getName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty.");
        } else {
            return name;
        }
    }

    /**
     * Modifie le nom du joueur.
     * @param name Le nouveau nom du joueur.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        } else if (name.length() > 20) {
            throw new IllegalArgumentException("Name cannot be longer than 20 characters.");
        } else {
            this.name = name;
        }
    }

    /**
     * Retourne la main du joueur.
     * @return La main du joueur.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Retourne si le joueur est un AI.
     * @return Si le joueur est un AI.
     */
    public boolean getIsAi() {
        return isAi;
    }

    /**
     * Gère le tirage des cartes.
     */
    public void handleDealCards() {
        hand.drawCardPlayerHand((byte) 2);
    }
}
