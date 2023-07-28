// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.security.InvalidParameterException;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.SensorTimeBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {
  private final CANCoderConfiguration config = new CANCoderConfiguration();

  private final int TURN_CAN_ID;
  private final int DRIVE_CAN_ID;
  private final int ENOCODER_CAN_ID;

  private final CANSparkMax turnMotor;
  private final CANSparkMax driveMotor;
  private final CANCoder turnEncoder;

  private boolean isActive = false;

  /** Creates a new SwerveModule. */
  public SwerveModule(int moduleNumber) {
    if (moduleNumber > 4 || moduleNumber < 1) {
      throw new InvalidParameterException("The module number must be between 1 and 4");
    }
    TURN_CAN_ID = moduleNumber;
    DRIVE_CAN_ID = moduleNumber + 10;
    ENOCODER_CAN_ID = moduleNumber;

    turnMotor = new CANSparkMax(TURN_CAN_ID, MotorType.kBrushless);
    driveMotor = new CANSparkMax(DRIVE_CAN_ID, MotorType.kBrushless);

    turnMotor.setSmartCurrentLimit(20);
    driveMotor.setSmartCurrentLimit(40);

    turnMotor.setIdleMode(IdleMode.kCoast);
    driveMotor.setIdleMode(IdleMode.kCoast);

    turnEncoder = new CANCoder(ENOCODER_CAN_ID);

    config.sensorCoefficient = 2 * Math.PI / 4096.0;
    config.unitString = "rad";
    config.sensorTimeBase = SensorTimeBase.PerSecond;

    turnEncoder.configAllSettings(config);
    turnEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 20);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getModuleAngle() {
    return turnEncoder.getAbsolutePosition();
  }

  public int getModuleNumber() {
    return TURN_CAN_ID;
  }

  public CommandBase activate() {
    return Commands.runOnce(() -> {
      isActive = true;
      driveMotor.setIdleMode(IdleMode.kBrake);
      turnMotor.setIdleMode(IdleMode.kBrake);
    }, this);
  }

  public CommandBase deactivate() {
    return Commands.runOnce(() -> {
      isActive = false;
      driveMotor.setIdleMode(IdleMode.kCoast);
      turnMotor.setIdleMode(IdleMode.kCoast);
    }, this);
  }

  public boolean isActive() {
    return isActive;
  }

  public void setTurnMotorSpeed(double percentange) {
    if (isActive) {
      turnMotor.set(percentange);
    }
  }

  public void setDriveMotorSpeed(double percentange) {
    if (isActive) {
      driveMotor.set(percentange);
    }
  }

}
