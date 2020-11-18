package team3647.frc2020.subsystems;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface PeriodicSubsystem extends Subsystem {
    default void init() {
    }

    default void end() {

    }

    default void readPeriodicInputs() {

    }

    default void writePeriodicOutputs() {

    }

    @Override
    default void periodic() {
        readPeriodicInputs();
        writePeriodicOutputs();
    }
    
}