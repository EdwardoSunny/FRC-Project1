package team3647.frc2020.commands;
 
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
 
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
 
public class HatchGrabber extends CommandBase {
    private final Solenoid hatchGrabber = new Solenoid(1);
    private boolean isOn;
    private double velocity;
 
    private boolean cargoDetection;
 
    public HatchGrabber(DoubleSupplier velocity, boolean isOn, BooleanSupplier cargoDetection) {
        this.velocity = velocity.getAsDouble();
        this.isOn = isOn;
        this.cargoDetection = cargoDetection.getAsBoolean();
    }
 
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
        
    }
 
    @Override
    public void execute() {
        if (cargoDetection) {
            System.out.println();
            if (velocity >= 4 && isOn) {
                hatchGrabber.set(true);
            } else {
                hatchGrabber.set(false);
            }
        }
        
    }
 
    @Override 
    public void end(boolean interrupted) {
    }  
 
    @Override 
    public boolean isFinished() {
        return true;
    }
 
 
}
