package team3647.frc2020.commands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class HatchGrabber extends CommandBase {
    private final Solenoid hatchGrabber = new Solenoid(1);
    private boolean isOn;
    private final Drivetrain dt;

    public HatchGrabber(Drivetrain dt, boolean isOn) {
        this.dt = dt;
        this.isOn = isOn;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        if (dt.getVelocity() >= 4 && isOn) {
            hatchGrabber.set(true);
        } else {
            hatchGrabber.set(false);
        }
    }

    @Override 
    public void end(boolean interrupted) {
        dt.end();
    }  

    @Override 
    public boolean isFinished() {
        return true;
    }


}