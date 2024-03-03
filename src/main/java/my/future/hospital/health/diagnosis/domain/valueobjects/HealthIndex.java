package my.future.hospital.health.diagnosis.domain.valueobjects;

import my.future.hospital.health.diagnosis.domain.exception.DomainException;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The health index is an integer which safeguards the domain from bad user input.
 * By design, if the instantiation succeeds, the health index is valid otherwise
 * an exception will be thrown.
 */
public class HealthIndex {

    private static final Pattern VALIDATION_REGEX = Pattern.compile("^-?\\d+$");

    private final int value;

    /**
     *
     * Accepts negative or positive integers.
     * However, patterns such as -789.50, 789.00, or 123.45 are not allowed.
     * @param uncheckedIndex the health index to be validated
     * @throws DomainException if {@code uncheckedIndex} has non-zero trailing decimals
     * or if it is not a 32 bits integer
     */
    public HealthIndex(String uncheckedIndex) throws DomainException {
        if (uncheckedIndex == null) {
            throw new IllegalArgumentException("The health index can not be null");
        }
        this.ensureIndexIsA32BitsInteger(uncheckedIndex);
        this.value = Integer.parseInt(uncheckedIndex);
    }

    /**
     * Returns the health index
     * @return an integer
     */
    public int getValue() {
        return value;
    }

    private void ensureIndexIsA32BitsInteger(String index) throws DomainException {

        if (!VALIDATION_REGEX.matcher(index).matches()) {
            throw new DomainException(String.format("The given health index \"%s\" is not a valid integer", index));
        }

        BigInteger maxInteger = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger minInteger = BigInteger.valueOf(Integer.MIN_VALUE);
        BigInteger value = new BigInteger(index);
        if (value.compareTo(maxInteger) > 0 || value.compareTo(minInteger) < 0)
        {
            throw new DomainException(
                    String.format("The given health index \"%s\" can not be smaller than %s nor greater than %s",
                            index, minInteger, maxInteger)
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthIndex that = (HealthIndex) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
