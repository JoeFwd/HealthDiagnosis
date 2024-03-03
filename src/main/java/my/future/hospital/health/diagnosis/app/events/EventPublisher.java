package my.future.hospital.health.diagnosis.app.events;

/**
 * Publish events to a system
 */
public interface EventPublisher {

    /**
     * Publishes the given event
     * @param event the event containing data.
     */
    public void publish(Event event);
}
