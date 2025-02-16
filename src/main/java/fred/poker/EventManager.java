package fred.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Classe qui permet de gérer les différents events du jeu
 * pub/sub classique
 */
public class EventManager {


    /* Map qui contient les différents events et les subscribers associés */
    private final Map<EventType, List<Consumer<EventType>>> subscribers = new HashMap<>();

    /**
     * Enumération des différents events possibles atm
     */
    public enum EventType {
        DEAL_CARDS, DEAL_FLOP, DEAL_TURN, DEAL_RIVER, ALL_IN, RAISE, CALL, FOLD, END_GAME
    }

    /**
     * Constructeur de la classe EventManager
     * init la map subscribers avec les différents events
     */
    public EventManager() {
        for (EventType event : EventType.values()) {
            subscribers.put(event, new ArrayList<>());
        }
    }

    /**
     * Permet d'incrire un subscriber à un event
     * @param event : l'event auquel on veut s'inscrire
     * @param subscriber : le subscriber qui s'inscrit
     * TODO Visibility scope
     */
    public void subscribe(EventType event, Consumer<EventType> subscriber) {
        subscribers.get(event).add(subscriber);
    }

    /**
     * Permet de notifier les subscribers d'un event
     * @param event : l'event à notifier
     * TODO Visibility scope
     */
    public void notifySubscribers(EventType event) {
        for (Consumer<EventType> subscriber : subscribers.get(event)) {
            subscriber.accept(event);
        }
    }

}
