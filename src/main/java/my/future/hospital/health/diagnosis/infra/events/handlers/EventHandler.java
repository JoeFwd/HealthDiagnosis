package my.future.hospital.health.diagnosis.infra.events.handlers;

import my.future.hospital.health.diagnosis.app.events.Event;

/**
 * Abstraction for handling events.
 * @param <T> the event type to handle.
 *           It must be a subtype of {@link Event}.
 */
public interface EventHandler<T extends Event> {

    /**
     * Handles the given event.
     * @param event the event to handle.
     */
    void handle(T event);
}
