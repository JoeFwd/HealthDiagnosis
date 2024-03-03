package my.future.hospital.health.diagnosis.domain.repositories;

import my.future.hospital.health.diagnosis.domain.entities.Diagnostic;

/**
 * Abstraction for the persistence of diagnostics
 */
public interface DiagnosticRepository {

    /**
     * Saves idempotently a diagnostic.
     * @param diagnostic a non-nullable diagnostic.
     * @return true if the diagnostic could be saved successfully, false otherwise.
     */
    boolean save(Diagnostic diagnostic);
}
