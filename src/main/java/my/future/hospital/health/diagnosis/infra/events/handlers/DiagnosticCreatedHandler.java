package my.future.hospital.health.diagnosis.infra.events.handlers;

import my.future.hospital.health.diagnosis.app.events.EventPublisher;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticCreated;
import my.future.hospital.health.diagnosis.app.queries.DiagnosticResultQuery;
import my.future.hospital.health.diagnosis.app.queries.DiagnosticResultQueryHandler;
import my.future.hospital.health.diagnosis.app.queries.readers.DiagnosticResultReader;
import my.future.hospital.health.diagnosis.domain.valueobjects.HealthIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the consequences of a diagnostic creation.
 */
public class DiagnosticCreatedHandler implements EventHandler<DiagnosticCreated> {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticCreatedHandler.class);

    private final DiagnosticResultReader diagnosticResultReader;

    private final EventPublisher eventPublisher;

    public DiagnosticCreatedHandler(DiagnosticResultReader diagnosticResultReader, EventPublisher eventPublisher) {
        this.diagnosticResultReader = diagnosticResultReader;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(DiagnosticCreated event) {
        HealthIndex healthIndex = event.getHealthIndex();
        if (healthIndex == null) {
            logger.error("The health index could not be retrieved from the DiagnosticCreated pojo.");
            return;
        }
        DiagnosticResultQuery query = new DiagnosticResultQuery(healthIndex);
        DiagnosticResultQueryHandler handler = new DiagnosticResultQueryHandler(diagnosticResultReader, eventPublisher);
        handler.handle(query);
    }
}
