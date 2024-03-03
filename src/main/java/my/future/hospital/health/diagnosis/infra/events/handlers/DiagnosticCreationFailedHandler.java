package my.future.hospital.health.diagnosis.infra.events.handlers;

import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticCreationFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.JavaBean;

/**
 * Handles the consequences of a diagnostic creation failure.
 */
public class DiagnosticCreationFailedHandler implements EventHandler<DiagnosticCreationFailed> {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticCreationFailedHandler.class);

    @Override
    public void handle(DiagnosticCreationFailed event) {
        logger.error("Diagnostic creation failed: {}", event.getFailureReason());
    }
}
