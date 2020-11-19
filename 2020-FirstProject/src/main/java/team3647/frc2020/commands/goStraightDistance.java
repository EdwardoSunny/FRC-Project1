package team3647.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class goStraightDistance extends CommandBase {
    private Drivetrain m_dt;
    private ArcadeDrive drive;
    private double distance;

    public goStraightDistance(Drivetrain m_dt, double targetDistance) {
        this.m_dt = m_dt;
        this.distance = targetDistance;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        m_dt.resetEncoders();
        m_dt.resetDistanceTraveled();
        super.initialize();
    }

    @Override
    public void execute() {
        m_dt.arcadeDrive(0, 1);
    }

    @Override 
    public void end(boolean interrupted) {
        m_dt.end();
    }  

    @Override 
    public boolean isFinished() {
        return m_dt.pIO.distanceTraveled >= distance;
    }
    
}