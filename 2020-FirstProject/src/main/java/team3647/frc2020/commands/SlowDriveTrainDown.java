package team3647.frc2020.commands;



import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class SlowDriveTrainDown extends CommandBase {
    private final Drivetrain m_dt;
    private static int pressCount = 0;

    public SlowDriveTrainDown(Drivetrain m_dt) {
        this.m_dt = m_dt;
        pressCount++;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        if (pressCount % 2 == 0) {
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