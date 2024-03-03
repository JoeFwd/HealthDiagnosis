package my.future.hospital.health.diagnosis.domain.entities.medicalunits;

import my.future.hospital.health.diagnosis.domain.entities.healthissues.HealthIssue;
import my.future.hospital.health.diagnosis.domain.entities.healthissues.BoneFracture;
import my.future.hospital.health.diagnosis.domain.entities.medicalunits.MedicalUnit;

import java.util.HashSet;
import java.util.Set;

/**
 * The traumatology medical unit handles all health issues related to wounds and
 * injuries caused by violence or general accidents.
 */
public class Traumatology extends MedicalUnit {

    private static final String TRAUMATOLOGY_NAME = "Traumatologie";

    @Override
    protected Set<HealthIssue> getTreatableHealthIssues() {
        Set<HealthIssue> healthIssues = new HashSet<>();
        healthIssues.add(new BoneFracture());
        return healthIssues;
    }

    @Override
    public String getName() {
        return TRAUMATOLOGY_NAME;
    }
}
