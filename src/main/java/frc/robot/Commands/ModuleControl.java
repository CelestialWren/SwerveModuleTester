// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.SwerveModule;

public class ModuleControl extends CommandBase {
  /** Creates a new ModuleControl. */
  private final SwerveModule module;
  private final Supplier<Double> turnSpeed, driveSpeed;
  public ModuleControl(SwerveModule module, Supplier<Double> turnSpeed, Supplier<Double> driveSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(module);
    this.module = module;
    this.turnSpeed = turnSpeed;
    this.driveSpeed = driveSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    module.setTurnMotorSpeed(turnSpeed.get()*.3);
    module.setDriveMotorSpeed(driveSpeed.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
