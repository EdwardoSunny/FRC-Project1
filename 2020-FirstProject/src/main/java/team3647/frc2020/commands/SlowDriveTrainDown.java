package team3647.frc2020.commands;



import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class SlowDriveTrainDown extends CommandBase {
    private final Drivetrain m_dt;
    private boolean pressed;

    public SlowDriveTrainDown(Drivetrain m_dt, boolean pressed) {
        this.m_dt = m_dt;
        if (constructCount % 2 == 0) {
            
        }
        pressed = true;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        if (constructCount % 2 == 0) {    
            m_dt.slow(true);
        } else {
            m_dt.slow(false);
        }
        
    }

    @Override 
    public void end(boolean interrupted) {
    }  

    @Override 
    public boolean isFinished() {
        return false;
    }
}