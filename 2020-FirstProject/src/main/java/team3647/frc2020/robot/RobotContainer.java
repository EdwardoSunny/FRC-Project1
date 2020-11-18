/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team3647.frc2020.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team3647.frc2020.subsystems.Drivetrain;
import team3647.lib.drivers.SparkMaxFactory;
import team3647.frc2020.commands.ArcadeDrive;
import team3647.frc2020.inputs.Joysticks;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final CANSparkMax leftMasterMotor = SparkMaxFactory.createSparkMax(Constants.leftMasterConfig);
  private final CANSparkMax leftSlaveMotor = SparkMaxFactory.createSparkMax(Constants.leftSlaveConfig);
  private final CANSparkMax rightMasterMotor = SparkMaxFactory.createSparkMax(Constants.rightMasterConfig);
  private final CANSparkMax rightSlaveMotor = SparkMaxFactory.createSparkMax(Constants.rightSlaveConfig);
  private final CANEncoder leftEncoder = leftMasterMotor.getEncoder();
  private final Joysticks controller = new Joysticks(0);
  public final Drivetrain dt = new Drivetrain(leftMasterMotor, leftSlaveMotor, rightMasterMotor, rightSlaveMotor, controller, leftEncoder);
  

  private final CommandScheduler m_commandScheduler = CommandScheduler.getInstance();

  public final Command autonomousCommand = new ArcadeDrive(dt, () -> {return 0;}, () -> {return 1;});

  public RobotContainer(){
    configButtonBindings();
    m_commandScheduler.registerSubsystem(dt);
    m_commandScheduler.setDefaultCommand(dt, new ArcadeDrive(dt, controller::getLeftStickY, controller::getRightStickX));
    
  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }

  private void configButtonBindings(){

  }

  public void init(){
    
  }
}
