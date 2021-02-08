package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import team3647.frc2020.robot.RobotContainer;
import team3647.lib.drivers.VictorSPXFactory;

public class HatchGrabber implements PeriodicSubsystem {
    private VictorSPX hatchSucker;
    private Solenoid hatchSolenoid;
    private double current = 0;
    private int hatchGrabPDPPin;
    private boolean extended;

    public HatchGrabber(VictorSPXFactory.Configuration hatchSuckerConfig, int hatchSolenoidPin, int hatchGrabPDPPin) {
        hatchSucker = VictorSPXFactory.createVictor(hatchSuckerConfig);
        hatchSolenoid = new Solenoid(hatchSolenoidPin);
        this.hatchGrabPDPPin = hatchGrabPDPPin;

    }

    public void extend(){
        hatchSolenoid.set(false);
        extended = true;
    }

    public void retract(){
        hatchSolenoid.set(true);
        extended = false;
    }

    private void updateCurrent() {
        current = RobotContainer.pDistributionPanel.getCurrent(hatchGrabPDPPin);
    }

    private double limitCurrent(double motorConst, double currentConst) {
        updateCurrent();
        System.out.println("Hatch Motor current: " + current);
        return current > currentConst ? (currentConst / current) * motorConst : motorConst;

    }

    public void grabHatch() {
        double output = limitCurrent(1, 15);
        hatchSucker.set(ControlMode.PercentOutput, output);
    }

    public void releaseHatch() {
        hatchSucker.set(ControlMode.PercentOutput, -limitCurrent(1, 15));
    }

    public void setOpenLoop(double demand) {
        hatchSucker.set(ControlMode.PercentOutput, demand);
    }

    public void runConstant() {
        setOpenLoop(.2);
    }
    
    public void stop(){
        setOpenLoop(0);
    }

    public boolean isExtended() {
        return extended;
    }

    
    @Override
    public String getName() {
        return "Hatch Grabber";
    }
}