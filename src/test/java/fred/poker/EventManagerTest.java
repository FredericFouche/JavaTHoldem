package fred.poker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerTest {
    @Test
    public void testSubscribeAndNotify() {
        EventManager manager = new EventManager();
        List<EventManager.EventType> receivedEvents = new ArrayList<>();

        Consumer<EventManager.EventType> subscriber = receivedEvents::add;
        manager.subscribe(EventManager.EventType.DEAL_CARDS, subscriber);

        assertTrue(receivedEvents.isEmpty(), "La liste des événements reçus doit être vide avant notification.");

        manager.notifySubscribers(EventManager.EventType.DEAL_CARDS);

        assertEquals(1, receivedEvents.size(), "Le subscriber doit être notifié une fois.");
        assertEquals(EventManager.EventType.DEAL_CARDS, receivedEvents.get(0), "L'événement reçu doit être DEAL_CARDS.");
    }

    @Test
    public void testMultipleSubscribers() {
        EventManager manager = new EventManager();
        List<String> log = new ArrayList<>();

        Consumer<EventManager.EventType> subscriber1 = event -> log.add("subscriber1:" + event);
        Consumer<EventManager.EventType> subscriber2 = event -> log.add("subscriber2:" + event);

        manager.subscribe(EventManager.EventType.DEAL_FLOP, subscriber1);
        manager.subscribe(EventManager.EventType.DEAL_FLOP, subscriber2);

        manager.notifySubscribers(EventManager.EventType.DEAL_FLOP);

        assertEquals(2, log.size(), "Les deux subscribers doivent être notifiés.");
        assertEquals("subscriber1:" + EventManager.EventType.DEAL_FLOP, log.get(0), "Le premier subscriber doit être notifié en premier.");
        assertEquals("subscriber2:" + EventManager.EventType.DEAL_FLOP, log.get(1), "Le deuxième subscriber doit être notifié en second.");
    }

    @Test
    public void testSubscribersNotCalledForOtherEvents() {
        EventManager manager = new EventManager();
        List<EventManager.EventType> receivedEvents = new ArrayList<>();

        Consumer<EventManager.EventType> subscriber = receivedEvents::add;
        manager.subscribe(EventManager.EventType.DEAL_TURN, subscriber);

        manager.notifySubscribers(EventManager.EventType.DEAL_RIVER);

        assertTrue(receivedEvents.isEmpty(), "Le subscriber inscrit sur DEAL_TURN ne doit pas être notifié pour DEAL_RIVER.");
    }

    @Test
    public void testNotifyWithoutSubscribers() {
        EventManager manager = new EventManager();
        try {
            manager.notifySubscribers(EventManager.EventType.END_GAME);
        } catch (Exception e) {
            fail("notifySubscribers a levé une exception pour un événement sans subscribers.");
        }
    }
}