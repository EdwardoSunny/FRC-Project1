package team3647.frc2020.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team3647.frc2020.inputs.Joysticks;

public class Drivetrain implements PeriodicSubsystem {
    private CANSparkMax leftMasterMotor;
    private CANSparkMax leftSlaveMotor;
    private CANSparkMax rightMasterMotor;
    private CANSparkMax rightSlaveMotor;
    private CANEncoder leftEncoder;
    private Joysticks controller;
    private DifferentialDrive m_drive;

    public periodicIO pIO = new periodicIO();

    private double currentRevs;

    public Drivetrain(CANSparkMax lM, CANSparkMax lS, CANSparkMax rM, CANSparkMax rS, Joysticks controller, CANEncoder leftEncoder) {
        leftMasterMotor = lM;
        leftSlaveMotor = lS;
        rightMasterMotor = rM;
        rightSlaveMotor = rS;

        rightSlaveMotor.follow(rightMasterMotor);
        leftSlaveMotor.follow(leftMasterMotor);
        leftMasterMotor.setInverted(false);
        rightMasterMotor.setInverted(true);

        this.leftEncoder = leftEncoder;
        this.controller = controller;
        m_drive = new DifferentialDrive(leftMasterMotor, rightMasterMotor);
    }

    public static class periodicIO {
        public double distanceTraveled;
    }

    public void init() {
        
    }

    public void end() {
        //stops everything
        leftMasterMotor.set(0);
        leftSlaveMotor.set(0);
        rightMasterMotor.set(0);
        rightSlaveMotor.set(0);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        xSpeed *= 0.6;
        zRotation *= 0.3;
        m_drive.arcadeDrive(xSpeed, zRotation);
    }

    public void readPeriodicInputs() {
        double rotations = leftEncoder.getPosition();
        pIO.distanceTraveled = rotations * 6 * Math.PI;
    }

    public void writePeriodicOutputs() {
    }

    @Override
    public void periodic() {
        //when its regeistered in the commandScheuler --> every command interation, this is called before the command
        PeriodicSubsystem.super.periodic();
    }
}