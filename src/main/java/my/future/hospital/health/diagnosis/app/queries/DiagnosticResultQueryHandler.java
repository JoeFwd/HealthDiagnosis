package my.future.hospital.health.diagnosis.app.queries;

import my.future.hospital.health.diagnosis.app.events.EventPublisher;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticResultFound;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticResultNotFound;
import my.future.hospital.health.diagnosis.app.queries.readers.DiagnosticResultReader;

import java.util.Optional;

/**
 * Gets the medical units associated to the health index
 * and triggers the NotifyUser event with this information.
 */
public class DiagnosticResultQueryHandler {

    private final DiagnosticResultReader diagnosticResultReader;

    private final EventPublisher eventPublisher;

    public DiagnosticResultQueryHandler(DiagnosticResultReader diagnosticResultReader, EventPublisher eventPublisher) {
        this.diagnosticResultReader = diagnosticResultReader;
        this.eventPublisher = eventPublisher;
    }

    public void handle(DiagnosticResultQuery query) {
        Optional<String> result = diagnosticResultReader.getDiagnosticResult(query.healthIndex());
        if (result.isPresent()) {
            this.eventPublisher.publish(new DiagnosticResultFound(query.healthIndex(), result.get()));
        } else {
            this.eventPublisher.publish(new DiagnosticResultNotFound(query.healthIndex()));
        }
    }
}
