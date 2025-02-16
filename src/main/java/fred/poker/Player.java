package fred.poker;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Player class that represents a player or an AI in the game.
 */
public class Player implements Consumer<EventManager.EventType> {
    private String name;
    private boolean isAi;
    private final Hand hand;
    private EventManager eventManager;
    private enum playerEventEmitter {
        CALL,
        RAISE,
        FOLD,
        ALL_IN
    }

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
        this.eventManager = eventManager;
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

    public void emitEvent(EventManager.EventType eventType) {
        eventManager.notifySubscribers(eventType);
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
        hand.addCardToPlayerHand(Table.deck.draw());
        hand.addCardToPlayerHand(Table.deck.draw());
    }

    /*
     * TODO : Algorithmes de décision pour les AI.
     */
    public void makeDecisionAi(boolean debug, Deck actualDeck) {
        long startTime = 0;
        if (debug) {
            startTime = System.nanoTime();
        }

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
        System.out.println("Deck : " + actualDeck.toString());
        int winrate = monteCarloSimulation(500, handValue[5]);
        String str =
                "Win rate : " + winrate + "%" + "\n" +
                "Hand value : " + handValue[5];
        if (winrate > 90) {
            if (handValue[5] < 1000) {
                playerEventEmitter("ALL_IN");
            } else {
                playerEventEmitter("RAISE");
            }
        } else if (winrate > 60) {
            if (handValue[5] > 1000 && handValue[5] < 1609) {
                playerEventEmitter("RAISE");
            } else {
                playerEventEmitter("CALL");
            }
        } else if (winrate > 30) {
            playerEventEmitter("CALL");
        } else {
            playerEventEmitter("FOLD");
        }

        if (debug) {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            str +=  "\n"
                    +"Duration : " + duration + "ms" + "\n" +
                    "-----------------------------------" + "\n" +
                    "        Next decision : "+ "\n" +
                    "-----------------------------------";
            System.out.println(str);
        }
    }

    public void playerEventEmitter(String event) {
        eventManager.notifySubscribers(EventManager.EventType.valueOf(playerEventEmitter.CALL.toString()));
    }

    public static int monteCarloSimulation(int nbOfTrials, int handValue) {
        int wins = 0;

        for (int i = 0; i < nbOfTrials; i++) {
            Deck tempDeck = new Deck();
            tempDeck.shuffle();
            int[] opponentHand = Hand.randomHand(tempDeck);
            int opponentHandRank = Evaluator.evaluateHand(opponentHand);
            if (handValue < opponentHandRank) {
                wins++;
            }
            tempDeck.destroyInstance();
        }
        return doubleToPercent((double) wins / nbOfTrials);
    }

    // --- Double to Percent(Int) conversion ---
    public static int doubleToPercent(double d) {
        return (int) (d * 100);
    }
}
