package team3647.frc2020.robot;
/*----------------------------------------------------------------------------*/

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;


public final class Constants {

    public static final int leftMasterPin = 9;
	public static final int leftSlave1Pin = 12; // 10
	public static final int leftSlave2Pin = 13; // 11

	public static final int rightMasterPin = 4;
	public static final int rightSlave1Pin = 5;
	public static final int rightSlave2Pin = 6;
     
        public static final int ElevatorGearboxSRXPin = 8;
        public static final int ElevatorGearboxSPX1Pin = 10;
        public static final int ElevatorGearboxSPX2Pin = 11; 
     
        public static final int hoodPWMPortChannel = 2;
        public static final int canifierID = 0;
     
        public static final int stallCurrent = 35;
        public static final int maxCurrent = 60;
        public static final int driveContinuousCurrent = 35;
     
        public static int kElevatorContinuousCurrent = 25;
        public static final int elevatorBeamBreakPin = 8;
        
        public static final int kTimeoutMs = 10;
     
     
        //drivetrain configs
     
        public static final TalonSRXFactory.Configuration leftMasterConfig =
        new TalonSRXFactory.Configuration(leftMasterPin, true)
                    .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
                    .voltageCompensation(true, 12.0);
     
        public static final TalonSRXFactory.Configuration rightMasterConfig =
            new TalonSRXFactory.Configuration(rightMasterPin, true)
                    .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
                    .voltageCompensation(true, 12.0);
     
        public static final VictorSPXFactory.Configuration leftSlave1Config =
            new VictorSPXFactory.Configuration(leftSlave1Pin).configMaxOutput(maxCurrent).configMaxReverseOutput(stallCurrent);
        
        public static final VictorSPXFactory.Configuration leftSlave2Config =
            new VictorSPXFactory.Configuration(leftSlave2Pin).configMaxOutput(maxCurrent).configMaxReverseOutput(stallCurrent);
     
        public static final VictorSPXFactory.Configuration rightSlave1Config =
            new VictorSPXFactory.Configuration(rightSlave1Pin).configMaxOutput(maxCurrent).setInverted(true).configMaxReverseOutput(stallCurrent);
        
        public static final VictorSPXFactory.Configuration rightSlave2Config =
            new VictorSPXFactory.Configuration(rightSlave2Pin).configMaxOutput(maxCurrent).setInverted(true).configMaxReverseOutput(stallCurrent);
    
    //Elevator configs
    public static final TalonSRXFactory.Configuration ElevatorMasterConfig =
    new TalonSRXFactory.Configuration(ElevatorGearboxSRXPin, true)
            .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
            .voltageCompensation(true, 12.0);

    public static final VictorSPXFactory.Configuration ElevatorSPX1Config = 
        new VictorSPXFactory.Configuration(ElevatorGearboxSPX1Pin).configMaxOutput(maxCurrent).setInverted(false).configMaxReverseOutput(stallCurrent);

    public static final VictorSPXFactory.Configuration ElevatorSPX2Config = 
        new VictorSPXFactory.Configuration(ElevatorGearboxSPX2Pin).configMaxOutput(maxCurrent).setInverted(false)
                .configMaxReverseOutput(stallCurrent);
}
