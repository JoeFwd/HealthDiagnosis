package my.future.hospital.health.diagnosis.domain.entities.healthissues;

import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Objects;

public class HeartHealthIssue implements HealthIssue {

    private final String HEART_HEALTH_ISSUE = "problème cardiaque";

    @Override
    public boolean isDetected(HealthIndex healthIndex) {
        return healthIndex.getValue() % 3 == 0;
    }

    @Override
    public String toString() {
        return HEART_HEALTH_ISSUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartHealthIssue that = (HeartHealthIssue) o;
        return Objects.equals(HEART_HEALTH_ISSUE, that.HEART_HEALTH_ISSUE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(HEART_HEALTH_ISSUE);
    }
}
