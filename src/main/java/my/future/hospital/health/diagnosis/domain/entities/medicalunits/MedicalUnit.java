package my.future.hospital.health.diagnosis.domain.entities.medicalunits;

import my.future.hospital.health.diagnosis.domain.entities.healthissues.HealthIssue;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Objects;
import java.util.Set;

/**
 * A medical unit is a facility where specific health issues can be looked at.
 */
public abstract class MedicalUnit {

    private final Set<HealthIssue> treatableHealthIssues = getTreatableHealthIssues();

    private final String name = getName();

    /**
     * Gets all the health issues that the medical unit can address.
     * @return a non-nullable <code>Set</code> containing all health issues.
     */
    protected abstract Set<HealthIssue> getTreatableHealthIssues();

    /**
     * Gets the name of the medical unit
     * @return a non-empty string
     */
    public abstract String getName();

    /**
     * Checks whether the health issue can be treated in this facility.
     *
     * @param healthIndex A non-nullable HealthIndex instance.
     * @return true if the health issue can be addressed in this unit, false otherwise.
     * @throws IllegalArgumentException if {@code healthIndex} is null
     */
    public boolean isHealthIndexTreatable(HealthIndex healthIndex) {
        if (healthIndex == null) throw new IllegalArgumentException("Expected a HealthIndex instance instead of null.");
        return treatableHealthIssues.stream().anyMatch(healthIssue -> healthIssue.isDetected(healthIndex));
    }

    /**
     * Checks the equality of two MedicalUnits
     * @param otherMedicalUnit another object.
     * @return true if {@code otherMedicalUnit} treats the same health issues and if it has the same name.
     */
    @Override
    public boolean equals(Object otherMedicalUnit) {
        if (this == otherMedicalUnit) return true;
        if (otherMedicalUnit == null || getClass() != otherMedicalUnit.getClass()) return false;
        MedicalUnit that = (MedicalUnit) otherMedicalUnit;
        return Objects.equals(treatableHealthIssues, that.treatableHealthIssues) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatableHealthIssues, name);
    }
}
