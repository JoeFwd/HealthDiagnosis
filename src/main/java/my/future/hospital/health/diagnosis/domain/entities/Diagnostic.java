package my.future.hospital.health.diagnosis.domain.entities;

import my.future.hospital.health.diagnosis.domain.entities.medicalunits.MedicalUnit;
import my.future.hospital.health.diagnosis.domain.entities.medicalunits.Cardiology;
import my.future.hospital.health.diagnosis.domain.entities.medicalunits.Traumatology;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The diagnostic is the aggregate root of this domain.
 */
public record Diagnostic(HealthIndex healthIndex) {

    private static final Set<MedicalUnit> medicalUnits = Set.of(new Cardiology(), new Traumatology());

    /**
     * @param healthIndex a non-nullable HealthIndex instance
     * @throws IllegalArgumentException if {@code healthIndex} is null
     */
    public Diagnostic {
        if (healthIndex == null) throw new IllegalArgumentException("Expected a HealthIndex instance instead of null");
    }

    /**
     * Gets the medical units where the patient should be redirected to.
     *
     * @return a set of medical units
     */
    public Set<MedicalUnit> redirectPatientToMedicalUnits() {
        return medicalUnits
                .stream()
                .filter(medicalUnit -> medicalUnit.isHealthIndexTreatable(this.healthIndex))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the health index
     *
     * @return a non-nullable health index
     */
    @Override
    public HealthIndex healthIndex() {
        return healthIndex;
    }
}
