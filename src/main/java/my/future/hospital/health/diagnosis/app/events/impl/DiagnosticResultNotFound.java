package my.future.hospital.health.diagnosis.app.events.impl;

import my.future.hospital.health.diagnosis.app.events.Event;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

/**
 * This event is triggered when a diagnostic result could not be found.
 */
public class DiagnosticResultNotFound implements Event {

    private final String id = "DiagnosticResultNotFound";

    private final HealthIndex healthIndex;

    /**
     * @param healthIndex the health index which result could not be found.
     */
    public DiagnosticResultNotFound(HealthIndex healthIndex) {
        this.healthIndex = healthIndex;
    }

    /**
     * Gets the health index which result could not be found.
     * @return the health index.
     */
    public HealthIndex getHealthIndex() {
        return healthIndex;
    }

    @Override
    public String getId() {
        return id;
    }
}
