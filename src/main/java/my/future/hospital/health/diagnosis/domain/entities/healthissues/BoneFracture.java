package my.future.hospital.health.diagnosis.domain.entities.healthissues;

import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Objects;

public class BoneFracture implements HealthIssue {

    private final String BONE_FRACTURE_NAME = "fracture";

    @Override
    public boolean isDetected(HealthIndex healthIndex) {
        return healthIndex.getValue() % 5 == 0;
    }

    @Override
    public String toString() {
        return BONE_FRACTURE_NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoneFracture that = (BoneFracture) o;
        return Objects.equals(BONE_FRACTURE_NAME, that.BONE_FRACTURE_NAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BONE_FRACTURE_NAME);
    }
}
