package my.future.hospital.health.diagnosis.app.events.impl;

import my.future.hospital.health.diagnosis.app.events.Event;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Objects;

public class DiagnosticResultFound implements Event {

    private final String id = "DiagnosticResultFound";

    private final HealthIndex healthIndex;

    private final String diagnosticResult;

    public DiagnosticResultFound(HealthIndex healthIndex, String diagnosticResult) {
        this.healthIndex = healthIndex;
        this.diagnosticResult = diagnosticResult;
    }

    public HealthIndex getHealthIndex() {
        return healthIndex;
    }

    public String getDiagnosticResult() {
        return diagnosticResult;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagnosticResultFound that = (DiagnosticResultFound) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
