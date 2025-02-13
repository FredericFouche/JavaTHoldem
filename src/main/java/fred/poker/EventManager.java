package fred.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventManager {
    private final Map<EventType, List<Consumer<EventType>>> subscribers = new HashMap<>();

    // Enum pour les différents event
    enum EventType {
        DEAL_CARDS, DEAL_FLOP, DEAL_TURN, DEAL_RIVER, END_GAME
    }

    // Constructeur qui boucle sur les différents event pour les ajouter à la map
    public EventManager() {
        for (EventType event : EventType.values()) {
            subscribers.put(event, new ArrayList<>());
        }
    }

    // Permet a un subscriber de s'abonner à un event en particulier en passant en paramètre l'event et le subscriber
    public void subscribe(EventType event, Consumer<EventType> subscriber) {
        subscribers.get(event).add(subscriber);
    }

    // Permet de notifier les subscribers d'un event en particulier en passant en paramètre l'event
    public void notifySubscribers(EventType event) {
        for (Consumer<EventType> subscriber : subscribers.get(event)) {
            subscriber.accept(event);
        }
    }

}
