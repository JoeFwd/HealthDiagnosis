package my.future.hospital.health.diagnosis.app.events.impl;

import my.future.hospital.health.diagnosis.app.events.Event;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Objects;

/**
 * An event which triggers when a diagnostic has been successfully created
 */
public class DiagnosticCreated implements Event {

    private final String id = "DiagnosticCreated";

    private final HealthIndex healthIndex;

    /**
     * @param healthIndex the health index of the created diagnostic
     */
    public DiagnosticCreated(HealthIndex healthIndex) {
        this.healthIndex = healthIndex;
    }

    /**
     * Gets the health index of the created diagnostic
     * @return the newly created health index
     */
    public HealthIndex getHealthIndex() {
        return healthIndex;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagnosticCreated that = (DiagnosticCreated) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
