package my.future.hospital.health.diagnosis.infra.events;

import my.future.hospital.health.diagnosis.app.events.Event;
import my.future.hospital.health.diagnosis.infra.events.handlers.EventHandler;

/**
 * Abstraction for subscribing to events from a remote system.
 */
public interface EventSubscriber {

     /**
     * Subscribes to an event of type {@code clazz}.
     * The given callback is executed when an event of the same type is published.
     * @param eventClass the event type to subscribe to.
     * @param eventHandler the callback to execute when an event of the same type is published.
     * @param <T> the event type.
     */
    <T extends Event> void subscribe(Class<T> eventClass, EventHandler<T> eventHandler);
}
