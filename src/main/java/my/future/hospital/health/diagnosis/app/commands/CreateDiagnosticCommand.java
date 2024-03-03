package my.future.hospital.health.diagnosis.app.commands;

/**
 * A command to create a diagnostic.
 */
public class CreateDiagnosticCommand
{
    private String healthIndex;

    public CreateDiagnosticCommand(String healthIndex) {
        this.healthIndex = healthIndex;
    }

    public String getHealthIndex() {
        return healthIndex;
    }
}
