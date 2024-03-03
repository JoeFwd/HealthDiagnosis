package my.future.hospital.health.diagnosis.app.events;

/**
 * A event represents a set of changes to data.
 */
public interface Event {

    /**
     * Gets an unique identifier
     * @return a non-nullable string
     */
    String getId();
}
