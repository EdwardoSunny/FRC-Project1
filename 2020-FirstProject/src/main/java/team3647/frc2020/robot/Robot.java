/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team3647.frc2020.robot;


import com.ctre.phoenix.motorcontrol.*;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team3647.lib.drivers.SparkMaxFactory;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */


public class Robot extends TimedRobot {
  public static RobotContainer m_RobotContainer;
  public static Command m_autonomousCommand;

  

  public void robotInit(){
    //runs when you press enable --> for every Init
    m_RobotContainer = new RobotContainer();
  }

  public void robotPeriodic(){
    CommandScheduler.getInstance().run();
    //every around 20ms after press enable --> for every periodic
  }

  public void teleopInit(){
  }

  public void teleopPeriodic(){
    CommandScheduler.getInstance().run();
  }

  public void autonomousInit(){
    m_autonomousCommand = m_RobotContainer.getAutonomousCommand();
    m_autonomousCommand.schedule();
  }

  public void autonomousPeriodic(){
    if (m_RobotContainer.dt.pIO.distanceTraveled >= 120) {
      m_RobotContainer.dt.end();
    } else{
      CommandScheduler.getInstance().run();
    }
  }
}
