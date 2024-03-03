package my.future.hospital.health.diagnosis.app.commands;

import my.future.hospital.health.diagnosis.app.events.EventPublisher;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticCreated;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticCreationFailed;
import my.future.hospital.health.diagnosis.domain.entities.Diagnostic;
import my.future.hospital.health.diagnosis.domain.exception.DomainException;
import my.future.hospital.health.diagnosis.domain.repositories.DiagnosticRepository;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;
import org.slf4j.Logger;

/**
 * Creates a diagnostic with the given health index.
 * Triggers the DiagnosticCreated event if the diagnostic succeeds.
 * A DiagnosticCreationFailed event is triggered if it failed.
 */
public class CreateDiagnosticCommandHandler
{
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CreateDiagnosticCommandHandler.class);

    private final DiagnosticRepository diagnosticRepository;

    private final EventPublisher eventPublisher;

    /**
     * @param diagnosticRepository the repository enabling the storage of diagnostics
     * @param eventPublisher the event publisher to notify the system of the successful diagnostic creation
     */
    public CreateDiagnosticCommandHandler(DiagnosticRepository diagnosticRepository, EventPublisher eventPublisher) {
        this.diagnosticRepository = diagnosticRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handle the diagnostic creation command
     * @param command the command containing the health index
     */
    public void handle(CreateDiagnosticCommand command) {
        try {
            HealthIndex validHealthIndex = new HealthIndex(command.getHealthIndex());
            Diagnostic diagnostic = new Diagnostic(validHealthIndex);
            this.diagnosticRepository.save(diagnostic);
            this.eventPublisher.publish(new DiagnosticCreated(validHealthIndex));
        } catch (DomainException e) {
            this.eventPublisher.publish(new DiagnosticCreationFailed(e.getLocalizedMessage()));
        }
    }
}
