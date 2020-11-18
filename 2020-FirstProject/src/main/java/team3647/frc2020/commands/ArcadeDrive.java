package team3647.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;
import java.util.function.DoubleSupplier;

public class ArcadeDrive extends CommandBase{
    private Drivetrain m_dt;
    private DoubleSupplier turn;
    private DoubleSupplier throttle;

    public ArcadeDrive(Drivetrain m_dt, DoubleSupplier turn, DoubleSupplier throttle) {
        this.m_dt = m_dt;
        this.turn = turn;
        this.throttle = throttle;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        m_dt.arcadeDrive(throttle.getAsDouble(), turn.getAsDouble());
    }

    @Override 
    public void end(boolean interrupted) {
        m_dt.end();
    }  

    @Override 
    public boolean isFinished() {
        return false;
    }
    
}