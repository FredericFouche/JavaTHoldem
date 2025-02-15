package fred.poker;

import java.util.List;
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

    /*
     * TODO : Algorithmes de décision pour les AI.
     */
    public void makeDecisionAi() {
        // On recupère la main du joueur et les cartes de la table
        List<Card> handToEval = hand.getCompleteHand();
        // On récupère la meilleure main possible dans les 7 cartes dispo
        int[] handValue = Hand.findBestHandInRange(handToEval, true);
        // handValue[6] contient le rang de la main
        /* public static final int MAX_STRAIGHT_FLUSH = 10;
        public static final int MAX_FOUR_OF_A_KIND   = 166;
        public static final int MAX_FULL_HOUSE       = 322;
        public static final int MAX_FLUSH            = 1599;
        public static final int MAX_STRAIGHT         = 1609;
        public static final int MAX_THREE_OF_A_KIND  = 2467;
        public static final int MAX_TWO_PAIR         = 3325;
        public static final int MAX_PAIR             = 6185; */
        // On peut utiliser ces valeurs pour déterminer la force de la main (de 5 cartes minimum) dans toute les mains
        // Mais ça ne suffit pas pour déterminer la décision à prendre pour l'AI
        // car il faut aussi prendre en compte les probabilités de gagner face aux autres joueurs
        // On peut utiliser la simulation de Monte Carlo pour ça
        if (handValue[6] >= 6185) {
            // On a une paire ou moins
            // On peut fold
        } else if (handValue[6] >= 3325) {
            // On a deux paires ou moins
            // On peut call
        } else if (handValue[6] >= 2467) {
            // On a un brelan ou moins
            // On peut raise
        } else if (handValue[6] >= 1609) {
            // On a une suite ou moins
            // On peut all-in
        } else if (handValue[6] >= 1599) {
            // On a une couleur ou moins
            // On peut all-in
        } else if (handValue[6] >= 322) {
            // On a un full ou moins
            // On peut all-in
        } else if (handValue[6] >= 166) {
            // On a un carré ou moins
            // On peut all-in
        } else {
            // On a une quinte flush ou mieux
            // On peut all-in
        }



        // TODO : Implémenter les algorithmes de décision pour les AI.
        // utilisation de la simulation de Monte Carlo
        // evaluation de la probabilité de gagner pour chaque AI
        // if score de main == très fort ou proba > 0.75 alors all-in
        // else if score de main == fort ou proba > 0.5 alors raise
        // else if score de main == moyen ou proba > 0.25 alors call
        // else fold

    }

    public static double monteCarloSimulation(int nbOfTrials, int handValue) {
        int wins = 0;
        for (int i = 0; i < nbOfTrials; i++) {
            int[] opponentHand = Hand.randomHand();
            int opponentHandRank = Evaluator.evaluateHand(opponentHand);
            if (handValue < opponentHandRank) {
                wins++;
            }
        }
        return (double) wins / nbOfTrials;
    }
}
