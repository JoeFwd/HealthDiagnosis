package my.future.hospital.health.diagnosis.infra;

import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticCreated;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticCreationFailed;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticResultFound;
import my.future.hospital.health.diagnosis.app.events.impl.DiagnosticResultNotFound;
import my.future.hospital.health.diagnosis.infra.events.DiagnosticCreationRequested;
import my.future.hospital.health.diagnosis.infra.events.InMemoryPubSubMessenger;
import my.future.hospital.health.diagnosis.infra.events.handlers.*;
import my.future.hospital.health.diagnosis.infra.repositories.InMemoryDiagnosticRepository;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final ExecutorService eventExecutor = Executors.newCachedThreadPool();

    private static final InMemoryDiagnosticRepository diagnosticRepository = new InMemoryDiagnosticRepository();

    private static final InMemoryPubSubMessenger pubSubMessenger = new InMemoryPubSubMessenger();

    public static void main(String[] args) {
        bootstrapEvents();

        for (int index = 0; index < Integer.MAX_VALUE ; index++) {
            int healthIndex = index;
            eventExecutor.submit(() -> requestDiagnosticCreation(Integer.toString(healthIndex)));
        }
    }

    private void scanUserInput() {
        Scanner stdin = new Scanner(new BufferedInputStream(System.in));
        while (stdin.hasNext()) {
            requestDiagnosticCreation(stdin.nextLine());
        }
    }

    private static void requestDiagnosticCreation(String healthIndex) {
        DiagnosticCreationRequested event = new DiagnosticCreationRequested(healthIndex);
        pubSubMessenger.publish(event);
    }

    private static void bootstrapEvents() {
        pubSubMessenger.subscribe(DiagnosticCreationRequested.class, new DiagnosticCreationRequestedHandler(diagnosticRepository, pubSubMessenger));
        pubSubMessenger.subscribe(DiagnosticCreated.class, new DiagnosticCreatedHandler(diagnosticRepository, pubSubMessenger));
        pubSubMessenger.subscribe(DiagnosticResultFound.class, new DiagnosticResultFoundHandler());
        pubSubMessenger.subscribe(DiagnosticResultNotFound.class, new DiagnosticResultNotFoundHandler());
        pubSubMessenger.subscribe(DiagnosticCreationFailed.class, new DiagnosticCreationFailedHandler());
    }
}
