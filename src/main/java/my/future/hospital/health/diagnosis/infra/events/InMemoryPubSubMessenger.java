package my.future.hospital.health.diagnosis.infra.events;

import my.future.hospital.health.diagnosis.app.events.EventPublisher;
import my.future.hospital.health.diagnosis.app.events.Event;
import my.future.hospital.health.diagnosis.infra.events.handlers.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.*;

/**
 * A in-memory implementation of the pub-sub messaging system.
 * It handles the events sequentially and synchronously.
 * @see EventPublisher
 * @see EventSubscriber
 * @see Event
 */
public class InMemoryPubSubMessenger implements EventPublisher, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryPubSubMessenger.class);

//    private static final ExecutorService eventExecutor = Executors.newFixedThreadPool(10);
//
//    private static final ExecutorService eventOrchestrator = Executors.newSingleThreadExecutor();
//
//    private static final LinkedList<Runnable> runnables = new LinkedList<>();
//
//    private static final Runnable runnable = () -> {
//
//        while(true) {
//            if (runnables.peekFirst() == null)
//                continue;
//            eventExecutor.execute(runnables.pop());
//        }
//    };
//
//    static {
//        eventOrchestrator.execute(runnable);
//    }

//    private static final Thread thread =  new Thread(() -> {
//        while (true) {
//            if (!runnables.isEmpty()) {
//                runnables.pop().run();
//            }
//        }
//    });

    private final Map<Class<Event>, List<EventHandler<Event>>> eventHandlers = new HashMap<>();

    @Override
    public void publish(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("The event must not be null.");
        }

        if (!eventHandlers.containsKey(event.getClass())) {
            logger.info("No subscribers for event: {}", event.getClass().getSimpleName());
            return;
        }

        eventHandlers.get(event.getClass()).forEach(callback -> {
            try {
                callback.handle(event);
            } catch (Exception e) {
                logger.error("An error occurred while handling the event {}", event.getClass().getSimpleName(), e);
            }
        });
    }

//    @Override
//    public void publish(Event event) {
//        if (event == null) {
//            throw new IllegalArgumentException("The event must not be null.");
//        }
//
//        if (!eventHandlers.containsKey(event.getClass())) {
//            logger.info("No subscribers for event: {}", event.getClass().getSimpleName());
//            return;
//        }
//
//        eventHandlers.get(event.getClass()).forEach(callback -> {
//            runnables.push(() -> {
//                try {
//                    callback.handle(event);
//                } catch (Exception e) {
//                    logger.error("An error occurred while handling the event {}", event.getClass().getSimpleName(), e);
//                }
//            });
//        });
//    }


    /**
     * @throws IllegalArgumentException if the eventClass or callback is null.
     * @throws UnsupportedOperationException if the eventClass is an interface or an abstract class.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Event> void subscribe(Class<T> eventClass, EventHandler<T> eventHandler) {
        if (eventClass == null || eventHandler == null) {
            throw new IllegalArgumentException("The eventClass and callback must not be null.");
        }

        if (eventClass.isInterface() || Modifier.isAbstract(eventClass.getModifiers())) {
            throw new UnsupportedOperationException("The eventClass must be an instantiable object.");
        }

        if (!eventHandlers.containsKey(eventClass)) {
            eventHandlers.put((Class<Event>) eventClass, new ArrayList<>());
        }
        eventHandlers.get(eventClass).add((EventHandler<Event>) eventHandler);
    }
}
