package team3647.frc2020.commands;
 
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
 
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.HatchGrabber;
 
public class GrabHatch extends CommandBase {
    private final HatchGrabber m_grabber;
    private final DoubleSupplier m_currentVelocity;
    private final boolean m_extend;
    private final boolean m_retract;
 
    public GrabHatch(HatchGrabber hatchGrabber, Boolean extend, Boolean retract, DoubleSupplier currentVelocity) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_grabber = hatchGrabber;
        addRequirements(m_grabber);
        m_extend = extend;
        m_retract = retract;
        m_currentVelocity = currentVelocity;
    
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    if (m_extend && Units.metersToFeet(m_currentVelocity.getAsDouble())<=4.0){
        
        m_grabber.extend();
        m_grabber.grabHatch();

    } else if (m_retract){

        m_grabber.retract();
        m_grabber.releaseHatch();

    } else if (m_grabber.isExtended()){

        m_grabber.runConstant();

    } else {

        m_grabber.stop();

    }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
    return false;
    }
 
 
}
