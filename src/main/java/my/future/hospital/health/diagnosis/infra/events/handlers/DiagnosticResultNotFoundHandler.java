package my.future.hospital.health.diagnosis.infra.events.handlers;

import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticResultNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiagnosticResultNotFoundHandler implements EventHandler<DiagnosticResultNotFound> {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticResultNotFoundHandler.class);

    @Override
    public void handle(DiagnosticResultNotFound event) {
        logger.error("Diagnostic result not found for health index: {}", event.getHealthIndex());
    }
}
