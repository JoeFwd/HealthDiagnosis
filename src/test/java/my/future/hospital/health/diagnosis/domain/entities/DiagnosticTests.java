package my.future.hospital.health.diagnosis.domain.entities;

import my.future.hospital.health.diagnosis.domain.entities.medicalunits.MedicalUnit;
import my.future.hospital.health.diagnosis.domain.entities.medicalunits.Cardiology;
import my.future.hospital.health.diagnosis.domain.entities.medicalunits.Traumatology;
import my.future.hospital.health.diagnosis.domain.exception.DomainException;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class DiagnosticTests {

    @Test
    public void Instantiation_WithNull_ThrowsAnError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Diagnostic(null));
    }

    @Test
    public void HealthIndex_UndividableBy3Or5_DoesNotRedirectThePatient() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("1");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        Assertions.assertTrue(medicalUnits.isEmpty());
    }

    @Test
    public void PositiveHealthIndex_AsMultipleOf3_RedirectsThePatientToCardiology() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("33");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        Assertions.assertEquals(Set.of(new Cardiology()), medicalUnits);
    }

    @Test
    public void NegativeHealthIndex_AsMultipleOf3_RedirectsThePatientToCardiology() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("-33");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        Assertions.assertEquals(Set.of(new Cardiology()), medicalUnits);
    }

    @Test
    public void PositiveHealthIndex_AsMultipleOf5_RedirectsThePatientToTraumatology() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("55");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        Assertions.assertEquals(Set.of(new Traumatology()), medicalUnits);
    }

    @Test
    public void NegativeHealthIndex_AsMultipleOf5_RedirectsThePatientToTraumatology() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("-55");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        Assertions.assertEquals(Set.of(new Traumatology()), medicalUnits);
    }

    @Test
    public void PositiveHealthIndex_AsMultipleOf3And5_RedirectsThePatientToCardiologyAndTraumatology() throws DomainException{
        HealthIndex healthIndex = new HealthIndex("15");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        // Making sure that the order does not matter
        Set<MedicalUnit> expectedMedicalUnits = Set.of(new Cardiology(), new Traumatology());
        Assertions.assertNotNull(medicalUnits);
        Assertions.assertTrue(medicalUnits.containsAll(expectedMedicalUnits));
    }

    @Test
    public void NegativeHealthIndex_AsMultipleOf3And5_RedirectsThePatientToCardiologyAndTraumatology() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("-15");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        // Making sure that the order does not matter
        Set<MedicalUnit> expectedMedicalUnits = Set.of(new Cardiology(), new Traumatology());
        Assertions.assertNotNull(medicalUnits);
        Assertions.assertTrue(medicalUnits.containsAll(expectedMedicalUnits));
    }

    @Test
    public void ZeroHealthIndex_RedirectsThePatientToCardiologyAndTraumatology() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("0");
        Diagnostic diagnostic = new Diagnostic(healthIndex);

        Set<MedicalUnit> medicalUnits = diagnostic.redirectPatientToMedicalUnits();

        // Making sure that the order does not matter
        Set<MedicalUnit> expectedMedicalUnits = Set.of(new Cardiology(), new Traumatology());
        Assertions.assertNotNull(medicalUnits);
        Assertions.assertTrue(medicalUnits.containsAll(expectedMedicalUnits));
    }
}
