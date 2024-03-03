package my.future.hospital.health.diagnosis.domain.valueobjects;

import my.future.hospital.health.diagnosis.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HealthIndexTests {

    @Test
    public void Instantiation_WithNull_ThrowsAnError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new HealthIndex(null));
    }

    @Test
    public void Instantiation_WithInvalidIntegerValue_IsForbidden() {
        Assertions.assertThrows(DomainException.class, () -> new HealthIndex("wrong Input"));
    }

    @Test
    public void Instantiation_WithNonIntegerDecimalNumber_IsForbidden() {
        Assertions.assertThrows(DomainException.class, () -> new HealthIndex("1.0000"));
    }

    @Test
    public void Instantiation_WithIntegerSmallerThanMinInteger_IsForbidden() {
        Assertions.assertThrows(DomainException.class, () -> new HealthIndex(Integer.MIN_VALUE + "0"));
    }

    @Test
    public void Instantiation_WithIntegerBiggerThanMaxInteger_IsForbidden() {
        Assertions.assertThrows(DomainException.class, () -> new HealthIndex(Integer.MAX_VALUE + "0"));
    }

    @Test
    public void Instantiation_WithInteger_IsAllowed() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("1");

        Assertions.assertEquals(1, healthIndex.getValue());
    }

    @Test
    public void Instantiation_WithNegativeInteger_IsAllowed() throws DomainException {
        HealthIndex healthIndex = new HealthIndex("-1");

        Assertions.assertEquals(-1, healthIndex.getValue());
    }
}
