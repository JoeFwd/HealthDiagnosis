package my.future.hospital.health.diagnosis.infra.events.handlers;

import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticResultFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the consequences of a diagnostic result being found.
 */
public class DiagnosticResultFoundHandler implements EventHandler<DiagnosticResultFound>{

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticResultFoundHandler.class);

    @Override
    public void handle(DiagnosticResultFound event) {
        String diagnosticResult = event.getDiagnosticResult();
        if (diagnosticResult == null) {
            logger.error("The diagnostic result could not be retrieved from the DiagnosticResultFound pojo.");
            return;
        }
        System.out.println(diagnosticResult);
        logger.debug("Diagnostic printed: {}", event.getHealthIndex());
    }
}
