package my.future.hospital.health.diagnosis.infra.events;

import my.future.hospital.health.diagnosis.app.events.Event;

/**
 * An event which triggers when a diagnostic creation has been requested
 */
public class DiagnosticCreationRequested implements Event {

    private final String id = "DiagnosticCreationRequested";

    private final String healthIndex;

    public DiagnosticCreationRequested(String healthIndex) {
        this.healthIndex = healthIndex;
    }

    public String getHealthIndex() {
        return healthIndex;
    }

    @Override
    public String getId() {
        return id;
    }
}
