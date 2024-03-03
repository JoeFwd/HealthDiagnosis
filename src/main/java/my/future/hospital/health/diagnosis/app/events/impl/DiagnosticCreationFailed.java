package my.future.hospital.health.diagnosis.app.events.impl;

import my.future.hospital.health.diagnosis.app.events.Event;

import java.util.Objects;

public class DiagnosticCreationFailed implements Event {

    private final String id = "DiagnosticCreationFailed";

    private final String failureReason;

    /**
     * @param failureReason the reason why the diagnostic failed
     */
    public DiagnosticCreationFailed(String failureReason) {
        this.failureReason = failureReason;
    }

    /**
     * @return the reason why the diagnostic failed
     */
    public String getFailureReason() {
        return failureReason;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagnosticCreationFailed that = (DiagnosticCreationFailed) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
