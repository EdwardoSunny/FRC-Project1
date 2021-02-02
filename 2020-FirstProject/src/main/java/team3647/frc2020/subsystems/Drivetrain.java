package team3647.frc2020.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.GeneralPin;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpiutil.math.MathUtil;
import team3647.frc2020.robot.Constants;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;
import team3647.lib.drivers.ClosedLoopFactory.ClosedLoopConfig;


public class Drivetrain implements PeriodicSubsystem {
  private final TalonSRX leftMaster;
  private final TalonSRX rightMaster;
  private final VictorSPX leftSlave1;
  private final VictorSPX leftSlave2;
  private final VictorSPX rightSlave1;
  private final VictorSPX rightSlave2; 

  private final ClosedLoopConfig leftMasterPIDConfig;
  private final ClosedLoopConfig rightMasterPIDConfig;

  private CANifier canifier;

  private double throttleMulti;
  private boolean isSlowed;

  private final double kWheelDiameter = Constants.driveWheelDiameter;


  public periodicIO p_IO = new periodicIO();
  public static final double kDefaultDeadband = 0.02;
  public static final double kDefaultMaxOutput = 1.0;

  protected double m_deadband = kDefaultDeadband;
  protected double m_maxOutput = kDefaultMaxOutput;
  private boolean squareInputs = false;

  //because config is already inverted
  private double m_rightSideInvertMultiplier = 1.0; 

  



  public Drivetrain(TalonSRXFactory.Configuration leftMasterConfig, TalonSRXFactory.Configuration rightMasterConfig, VictorSPXFactory.Configuration leftSlave1Config, 
  VictorSPXFactory.Configuration leftSlave2Config, VictorSPXFactory.Configuration rightSlave1Config, VictorSPXFactory.Configuration rightSlave2Config, 
  CANifier canifier, ClosedLoopConfig leftPIDConfig, ClosedLoopConfig rightPIDConfig) {
    //returns in ticks/100ms
    leftMaster = TalonSRXFactory.createTalon(leftMasterConfig);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
    rightMaster = TalonSRXFactory.createTalon(rightMasterConfig);

    leftSlave1 = VictorSPXFactory.createVictor(leftSlave1Config);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
    leftSlave2 = VictorSPXFactory.createVictor(leftSlave2Config);
    
    rightSlave1 = VictorSPXFactory.createVictor(rightSlave1Config);
    rightSlave2 = VictorSPXFactory.createVictor(rightSlave2Config);

    leftMasterPIDConfig = leftPIDConfig;
    rightMasterPIDConfig = rightPIDConfig;

    leftSlave1.follow(leftMaster);
    leftSlave2.follow(leftMaster);

    rightSlave1.follow(rightMaster);
    rightSlave2.follow(rightMaster);

    throttleMulti = 0.6;

  }  

  public static class periodicIO {
    public double distanceTraveled;
    public boolean hasNoCargo;
    public double leftPosition;
    public double rightPosition;
    public double leftVelocity;
    public double rightVelocity;
  }

  public void arcadeDrive(double xSpeed, double zRotation)  {
    xSpeed *= throttleMulti;
    zRotation *= 0.3;
    xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
    xSpeed = applyDeadband(xSpeed, m_deadband);

    zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
    zRotation = applyDeadband(zRotation, m_deadband);

    // Square the inputs (while preserving the sign) to increase fine control
    // while permitting full power.
    if (squareInputs) {
      xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
      zRotation = Math.copySign(zRotation * zRotation, zRotation);
    }

    double leftMotorOutput;
    double rightMotorOutput;

    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

    if (xSpeed >= 0.0) {
      // First quadrant, else second quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      } else {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      } else {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      }
    }

    leftMaster.set(ControlMode.PercentOutput, MathUtil.clamp(leftMotorOutput, -1.0, 1.0));
    
    rightMaster.set(ControlMode.PercentOutput, MathUtil.clamp(rightMotorOutput, -1.0, 1.0));
  }

  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  public void updateEncoders() {
    //convert from ticks to meter
    p_IO.leftPosition = (leftMaster.getSelectedSensorPosition() * leftMasterPIDConfig.kEncoderTicksToUnits);
    p_IO.rightPosition = (rightMaster.getSelectedSensorPosition() * rightMasterPIDConfig.kEncoderTicksToUnits);

    //convert from ticks/100ms to rev/sec to m/s
    p_IO.leftVelocity = leftMaster.getSelectedSensorVelocity() * leftMasterPIDConfig.kEncoderVelocityToRPM * kWheelDiameter * (1/60);
    p_IO.rightVelocity = rightMaster.getSelectedSensorVelocity() * leftMasterPIDConfig.kEncoderVelocityToRPM * kWheelDiameter * (1/60);
  }

  public void resetEncoders() {
    p_IO.leftPosition = 0;
    p_IO.rightPosition = 0;
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
    p_IO.leftVelocity = 0;
    p_IO.rightVelocity = 0;
  }

  public void updateDistanceTraveled() {
    p_IO.distanceTraveled = (p_IO.rightPosition + p_IO.leftPosition)/2;
  }

  public void resetDistanceTraveled(){
    p_IO.distanceTraveled = 0;
  }

  public void updateCargoDetection() {
    p_IO.hasNoCargo = cargoDetection();
  }

  public void setSlow(boolean slowed) {
    if (slowed && p_IO.hasNoCargo) {
        isSlowed = true;
        throttleMulti = 0.2;
    } else {
        isSlowed = false;
        throttleMulti = 0.6;
    }
  }

  public boolean getSlow() {
    return isSlowed;
  }

  public double getDistanceTraveled() {
    return p_IO.distanceTraveled;
  }

  public double getdtVelocity() {
    return (p_IO.leftVelocity + p_IO.rightVelocity)/2;
  }

  public boolean cargoDetection() {
    //returns true if no cargo, false if cargo
    return !canifier.getGeneralInput(GeneralPin.LIMR);
  }

  public void init() {
    resetEncoders();
    resetDistanceTraveled();
  }

  public void end() {
    //stops everything
    leftMaster.set(ControlMode.PercentOutput, 0);
    rightMaster.set(ControlMode.PercentOutput, 0);
  }

  public void readPeriodicInputs() {
    updateEncoders();
    updateDistanceTraveled();
    updateCargoDetection();
  }

  public void writePeriodicOutputs() {
  }

  @Override
  public void periodic() {
    //when its regeistered in the commandScheuler --> every command interation, this is called before the command
    PeriodicSubsystem.super.periodic();
      
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return "DriveTrain";
  }

}
