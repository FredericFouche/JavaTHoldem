package fred.poker;

import java.util.List;
import java.util.function.Consumer;

/**
 * Player class that represents a player or an AI in the game.
 */
public class Player implements Consumer<EventManager.EventType> {
    private String name;
    private boolean isAi;
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
        List<Card> handToEval = hand.getCompleteHand();
        int[] handValue;
        // On récupère la meilleure main possible dans les 7 cartes dispo
        if (handToEval.size() < 5) {
            System.out.println("Hand debug : " + handToEval);
            throw new IllegalArgumentException("Hand must contain at least 5 cards.");
        }

        handValue = Hand.findBestHandInRange(handToEval, true);

        // 1 est le rang le plus for et 7463 le rang le plus faible
        // L'algo doit estimer sur 100 itérations la probabilité de gagner
        // Il doit ensuite prendre une décision en fonction de la probabilité de gagner et de la force de la main
        int winrate = monteCarloSimulation(500, handValue[5]);
        String str =
                "Win rate : " + winrate + "%" + "\n" +
                "Hand value : " + handValue[5] + "\n" +
                "-----------------------------------"+ "\n" +
                "Next decision : " + "\n" +
                "-----------------------------------";
        if (winrate > 90) {
            if (handValue[5] < 1000) {
                System.out.println("All-in");
                System.out.println(str);
            } else {
                System.out.println("Raise");
                System.out.println(str);
            }
        } else if (winrate > 60) {
            if (handValue[5] > 1000 && handValue[5] < 1609) {
                System.out.println("Raise");
                System.out.println(str);
            } else {
                System.out.println("Call");
                System.out.println(str);
            }
        } else if (winrate > 30) {
            System.out.println("Call");
            System.out.println(str);
        } else {
            System.out.println("Fold");
            System.out.println(str);
        }

    }

    public static int monteCarloSimulation(int nbOfTrials, int handValue) {
        int wins = 0;
        for (int i = 0; i < nbOfTrials; i++) {
            int[] opponentHand = Hand.randomHand();
            int opponentHandRank = Evaluator.evaluateHand(opponentHand);
            if (handValue < opponentHandRank) {
                wins++;
            }
        }
        double winRate = (double) wins / nbOfTrials;
        return doubleToPercent(winRate);
    }

    // --- Double to Percent(Int) conversion ---
    public static int doubleToPercent(double d) {
        return (int) (d * 100);
    }
}
