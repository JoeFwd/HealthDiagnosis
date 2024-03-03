package my.future.hospital.health.diagnosis.domain.entities.healthissues;

import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

public interface HealthIssue {

    /**
     * Checks whether the health index is tied to the health issue.
     * @param healthIndex the healthIndex potentially matching the health issue.
     * @return true if the health index matches the health issue description, false otherwise.
     */
    boolean isDetected(HealthIndex healthIndex);
}
