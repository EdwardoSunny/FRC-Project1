package team3647.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class GoStraightDistance extends CommandBase {
    private Drivetrain m_dt;
    private double distance;

    public GoStraightDistance(Drivetrain dt, double targetDistance) {
        this.m_dt = dt;
        this.distance = targetDistance;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
        m_dt.resetDistanceTraveled();
        m_dt.resetEncoders();
    }

    @Override
    public void execute() {
        m_dt.arcadeDrive(0, 0.6);
    }

    @Override 
    public void end(boolean interrupted) {
        m_dt.end();
    }  

    @Override 
    public boolean isFinished() {
        return m_dt.getDistanceTraveled() >= distance;
    }
}