package team3647.frc2020.robot;

import com.ctre.phoenix.CANifier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import team3647.frc2020.commands.ArcadeDrive;
import team3647.frc2020.commands.GoStraightDistance;
import team3647.frc2020.commands.GrabHatch;
import team3647.frc2020.inputs.Joysticks;
import team3647.frc2020.subsystems.Drivetrain;
import team3647.frc2020.subsystems.Elevator;
import team3647.frc2020.subsystems.HatchGrabber;
import team3647.lib.wpi.PDP;

 
 
public class RobotContainer {
  private final Joysticks controller = new Joysticks(0);
  private final CANifier canifier = new CANifier(Constants.canifierID);
  public static PDP pDistributionPanel = new PDP();
 
  public final Drivetrain dt = new Drivetrain(Constants.leftMasterConfig, Constants.rightMasterConfig, Constants.leftSlave1Config, Constants.leftSlave2Config, Constants.rightSlave1Config, Constants.rightSlave1Config, canifier, Constants.leftMasterPIDConfig, Constants.rightMasterPIDConfig);
  public final Elevator elevator = new Elevator(Constants.ElevatorMasterConfig, Constants.ElevatorSPX1Config, Constants.ElevatorSPX2Config, dt::setSlow, true);
  public final HatchGrabber m_hatchGrabber = new HatchGrabber(Constants.hatchSuckerConfig, Constants.hatchGrabberSolinoidPin, Constants.hatchGrabberPDPpin);
  private final CommandScheduler m_commandScheduler = CommandScheduler.getInstance();
 
  public final Command autonomousCommand = new GoStraightDistance(dt, 10);
 
  public RobotContainer() {
    configButtonBindings();
    m_commandScheduler.registerSubsystem(dt);
    m_commandScheduler.setDefaultCommand(dt,
        new ArcadeDrive(dt, controller::getLeftStickY, controller::getRightStickX));
  }
 
  public Command getAutonomousCommand() {
    return autonomousCommand;
  }
 
  public boolean getDrivetrainSlowed() {
    return dt.getSlow();
  }
 
  public boolean getCargoCondition() {
    return dt.cargoDetection();
  }
 
  private void configButtonBindings(){
    controller.buttonX.whenActive(new InstantCommand(() -> dt.setSlow(!dt.getSlow()), dt));
    controller.leftBumper.whenActive(new GrabHatch(m_hatchGrabber, true, false, dt::getdtVelocity));
    controller.leftBumper.whenInactive(new GrabHatch(m_hatchGrabber, false, false, dt::getdtVelocity));
    controller.rightBumper.whenActive(new GrabHatch(m_hatchGrabber, false, true, dt::getdtVelocity));
    controller.rightBumper.whenInactive(new GrabHatch(m_hatchGrabber, false, false, dt::getdtVelocity));
 
 
    controller.buttonA.whenActive(new InstantCommand(() -> elevator.moveToBottom(), elevator));
    controller.buttonB.whenActive(new InstantCommand(() -> elevator.moveToMiddle(), elevator));
    controller.buttonY.whenActive(new InstantCommand(() -> elevator.moveToTop(), elevator));
  }
 
  public void init(){
    
  }
}
