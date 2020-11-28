package team3647.frc2020.commands;



import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class SlowDriveTrainDown extends CommandBase {
    private Drivetrain m_dt;

    public SlowDriveTrainDown(Drivetrain m_dt) {
        this.m_dt = m_dt;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        m_dt.slowDown();

    }

    @Override 
    public void end(boolean interrupted) {
        m_dt.returnNormal();
    }  

    @Override 
    public boolean isFinished() {
        return false;
    }
}