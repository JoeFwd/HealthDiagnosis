package my.future.hospital.health.diagnosis.app.queries.readers;

import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.Optional;

/**
 * Enables to get the processed result of a diagnostic.
 * The processed result allows the user to know to which medical units it has to go.
 */
public interface DiagnosticResultReader {

    /**
     * Gets the diagnostic processed result.
     * @param healthIndex the diagnostic health index to look for.
     * @return the diagnostic processed result or an empty optional instance if it could not be found.
     */
    Optional<String> getDiagnosticResult(HealthIndex healthIndex);

}
