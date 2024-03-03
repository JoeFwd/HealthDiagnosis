package my.future.hospital.health.diagnosis.domain.entities.medicalunits;

import my.future.hospital.health.diagnosis.domain.entities.healthissues.HealthIssue;
import my.future.hospital.health.diagnosis.domain.entities.healthissues.HeartHealthIssue;
import my.future.hospital.health.diagnosis.domain.entities.medicalunits.MedicalUnit;

import java.util.HashSet;
import java.util.Set;

/**
 * The cardiology medical unit handles all disorders of the heart.
 */
public class Cardiology extends MedicalUnit {

    private static final String CARDOLOGY_NAME = "Cardologie";

    @Override
    protected Set<HealthIssue> getTreatableHealthIssues() {
        Set<HealthIssue> healthIssues = new HashSet<>();
        healthIssues.add(new HeartHealthIssue());
        return healthIssues;
    }

    @Override
    public String getName() {
        return CARDOLOGY_NAME;
    }
}
