package team3647.frc2020.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain implements PeriodicSubsystem {
    private final CANSparkMax leftMasterMotor;
    private final CANSparkMax leftSlaveMotor;
    private final CANSparkMax rightMasterMotor;
    private final CANSparkMax rightSlaveMotor;
    private final CANEncoder leftEncoder;
    private final CANEncoder rightEncoder;

    private DifferentialDrive m_drive;

    private periodicIO pIO = new periodicIO();

    public Drivetrain(CANSparkMax lM, CANSparkMax lS, CANSparkMax rM, CANSparkMax rS) {
        leftMasterMotor = lM;
        leftSlaveMotor = lS;
        rightMasterMotor = rM;
        rightSlaveMotor = rS;

        rightSlaveMotor.follow(rightMasterMotor);
        leftSlaveMotor.follow(leftMasterMotor);
        leftMasterMotor.setInverted(false);
        rightMasterMotor.setInverted(true);

        this.leftEncoder = leftMasterMotor.getEncoder();
        this.rightEncoder = rightMasterMotor.getEncoder();
        m_drive = new DifferentialDrive(leftMasterMotor, rightMasterMotor);
        m_drive.setRightSideInverted(false);
    }

    public static class periodicIO {
        public double distanceTraveled;
    }

    public void init() {
        resetEncoders();
        resetDistanceTraveled();
    }

    public void end() {
        //stops everything
        leftMasterMotor.set(0);
        leftSlaveMotor.set(0);
        rightMasterMotor.set(0);
        rightSlaveMotor.set(0);
    }

    public void arcadeDrive(double xSpeed, double zRotation)  {
        xSpeed *= 0.6;
        zRotation *= 0.3;
        m_drive.arcadeDrive(xSpeed, zRotation);
    }
    
    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    public void resetDistanceTraveled(){
        pIO.distanceTraveled = 0;
    }

    public double getDistanceTraveled() {
        return pIO.distanceTraveled;
    }

    public void readPeriodicInputs() {
        double rotationsL = leftEncoder.getPosition();
        double rotationsR = leftEncoder.getPosition();
        double distanceL = rotationsL * 6 * Math.PI;
        double distanceR = rotationsR * 6 * Math.PI;

        pIO.distanceTraveled = (distanceL + distanceR)/2;
    }

    public void writePeriodicOutputs() {
    }

    @Override
    public void periodic() {
        //when its regeistered in the commandScheuler --> every command interation, this is called before the command
        PeriodicSubsystem.super.periodic();
    }
}