package team3647.frc2020.subsystems;
 
import java.util.function.Consumer;
 
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
 
import edu.wpi.first.wpilibj.DigitalInput;
import team3647.frc2020.robot.Constants;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory; 
 
public class Elevator implements PeriodicSubsystem {
    private final TalonSRX talon;
    private final VictorSPX gearBoxSPX1;
    private final VictorSPX gearBoxSPX2;
    //check if drivetrain is slowed
    private boolean driveSlowed;
 
 
    private int encoderValue;
    private double encoderVelocity;
    private boolean bannerSensorValue = false;
    private DigitalInput limitSwitch = new DigitalInput(Constants.elevatorBeamBreakPin);
 
    //setting slow drive
    private Consumer<Boolean> slowDrive;
    private boolean setDriveSlow;
 
    public Elevator(TalonSRXFactory.Configuration talonConfig, VictorSPXFactory.Configuration SPX1Config, VictorSPXFactory.Configuration SPX2Config, Consumer<Boolean> slowDrive, boolean driveTrainSlowed) {
        talon = TalonSRXFactory.createTalon(talonConfig);
        gearBoxSPX1 = VictorSPXFactory.createVictor(SPX1Config);
        gearBoxSPX2 = VictorSPXFactory.createVictor(SPX2Config);
        this.slowDrive = slowDrive;
        this.setDriveSlow = driveTrainSlowed;
        
        gearBoxSPX1.follow(talon);
        gearBoxSPX2.follow(talon);
        
    }
 
    public void init() {
 
    }
 
    public void setOpenLoop(double demand) {
        talon.set(ControlMode.PercentOutput, demand);
    }
 
    public void resetSRXEncoderValue() {
        encoderValue = 0;
        talon.setSelectedSensorPosition(0);
    }
 
    public void updateBannerSensor() {
        bannerSensorValue = limitSwitch.get();
    }
 
    public void updateSRXEncoder() {
        encoderValue = talon.getSelectedSensorPosition() ; //In ticks
        // so it only does it once instead of running the same method without doing anything every update?
        if (encoderValue >= 16000 && !driveSlowed) {
            slowDrive.accept(setDriveSlow);
            driveSlowed = true;
        }
        encoderVelocity = talon.getSelectedSensorVelocity() * (10/4096) * 0.5;
    }
 
    public void initEncoders() {
        talon.enableCurrentLimit(true);
        talon.configContinuousCurrentLimit(Constants.kElevatorContinuousCurrent);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
        talon.setSensorPhase(true);
        talon.setInverted(false);
        talon.setNeutralMode(NeutralMode.Brake);
        updateBannerSensor();
    }
 
    public boolean getBannerSensorValue() {
        return bannerSensorValue;
    }
 
    public void stop() {
        setOpenLoop(0);
    }
 
    public void moveToBottom() {
        //height 0
        if (getBannerSensorValue()) {
            stop();
            resetSRXEncoderValue();
        } else {
            setOpenLoop(-.3);
        }
    }
 
    public void moveToMiddle() {
        //height 12000
        setSRXPosition(12000);
    }
 
    public void moveToTop() {
        //height 23000
        setSRXPosition(23000);
    }
 
    public void setSRXPosition(double position) {
        talon.set(ControlMode.MotionMagic, position);
    }
 
 
 
    @Override
    public void readPeriodicInputs() {
        updateBannerSensor();
        updateSRXEncoder();
    }
 
    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        
    }
 
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
}
