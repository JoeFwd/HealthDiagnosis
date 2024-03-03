package my.future.hospital.health.diagnosis.infra.repositories;

import my.future.hospital.health.diagnosis.app.queries.readers.DiagnosticResultReader;
import my.future.hospital.health.diagnosis.domain.entities.Diagnostic;
import my.future.hospital.health.diagnosis.domain.repositories.DiagnosticRepository;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This repository holds all diagnostics in memory. Data will be lost when this object is garbage collected.
 */
public class InMemoryDiagnosticRepository implements DiagnosticRepository, DiagnosticResultReader {

    private final Map<HealthIndex, Diagnostic> savedDiagnostics = new HashMap<>();

    @Override
    public boolean save(Diagnostic diagnostic) {
        if (diagnostic == null) return false;
        savedDiagnostics.put(diagnostic.healthIndex(), diagnostic);
        return true;
    }

    /**
     * @throws IllegalArgumentException if {@code healthIndex} is null.
     */
    @Override
    public Optional<String> getDiagnosticResult(HealthIndex healthIndex) {
        if (healthIndex == null)
            throw new IllegalArgumentException("Expected an HealthIndex instance instead of null");

        Diagnostic diagnostic = savedDiagnostics.get(healthIndex);
        if (diagnostic == null)
            return Optional.empty();

        String diagnosticResult = diagnostic
                .redirectPatientToMedicalUnits()
                .stream()
                .map(medicalUnit -> medicalUnit.getName())
                .collect(Collectors.joining(", "));

        return Optional.of(diagnosticResult);
    }
}
