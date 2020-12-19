package team3647.frc2020.robot;
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import team3647.lib.drivers.SparkMaxFactory;
import com.revrobotics.CANSparkMax.IdleMode;

public final class Constants {
    public static final int leftMotor1Pin = 0;
    public static final int leftMotor2Pin = 1;
    public static final int rightMotor1Pin = 2;
    public static final int rightMotor2Pin = 3;
    public static final int stallCurrent = 35;
    public static final int maxCurrent = 60;

    public static final int canifierID = 0;

    public static final SparkMaxFactory.Configuration leftMasterConfig =
                new SparkMaxFactory.Configuration(leftMotor1Pin, false)
                        .currentLimiting(true, maxCurrent, stallCurrent).idleMode(IdleMode.kBrake)
                        .voltageCompensation(true, 12.0);
    public static final SparkMaxFactory.Configuration rightMasterConfig =
                new SparkMaxFactory.Configuration(rightMotor1Pin, true)
                        .currentLimiting(true, maxCurrent, stallCurrent).idleMode(IdleMode.kBrake)
                        .voltageCompensation(true, 12.0);


    public static final SparkMaxFactory.Configuration leftSlaveConfig =
                SparkMaxFactory.Configuration.mirrorWithCANID(leftMasterConfig, leftMotor2Pin);

    public static final SparkMaxFactory.Configuration rightSlaveConfig =
                SparkMaxFactory.Configuration.mirrorWithCANID(rightMasterConfig, rightMotor2Pin);

}
