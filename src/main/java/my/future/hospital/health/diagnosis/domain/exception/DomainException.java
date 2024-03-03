package my.future.hospital.health.diagnosis.domain.exception;

public class DomainException extends Exception {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Exception exception) {
        super(message, exception);
    }

}
