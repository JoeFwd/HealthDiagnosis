package my.future.hospital.health.diagnosis.infra.events.handlers;

import my.future.hospital.health.diagnosis.app.commands.CreateDiagnosticCommand;
import my.future.hospital.health.diagnosis.app.commands.CreateDiagnosticCommandHandler;
import my.future.hospital.health.diagnosis.app.events.EventPublisher;
import my.future.hospital.health.diagnosis.domain.repositories.DiagnosticRepository;
import my.future.hospital.health.diagnosis.infra.events.DiagnosticCreationRequested;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the creation of a diagnostic when requested.
 */
public class DiagnosticCreationRequestedHandler implements EventHandler<DiagnosticCreationRequested> {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticCreationRequestedHandler.class);

    private final DiagnosticRepository diagnosticRepository;

    private final EventPublisher eventPublisher;

    public DiagnosticCreationRequestedHandler(DiagnosticRepository diagnosticRepository, EventPublisher eventPublisher) {
        this.diagnosticRepository = diagnosticRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(DiagnosticCreationRequested event) {
        String healthIndex = event.getHealthIndex();
        if (healthIndex == null) {
            logger.error("The health index could not be retrieved from the DiagnosticCreationRequested pojo.");
            return;
        }

        CreateDiagnosticCommand query = new CreateDiagnosticCommand(healthIndex);
        CreateDiagnosticCommandHandler handler = new CreateDiagnosticCommandHandler(diagnosticRepository, eventPublisher);
        handler.handle(query);
    }
}
