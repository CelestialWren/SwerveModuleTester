// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.ModuleControl;
import frc.robot.Subsystems.SwerveModule;

public class RobotContainer {
  private final SwerveModule module1 = new SwerveModule(1);
  private final SwerveModule module2 = new SwerveModule(2);
  private final SwerveModule module3 = new SwerveModule(3);
  private final SwerveModule module4 = new SwerveModule(4);

  private final CommandXboxController controller = new CommandXboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    module1.setDefaultCommand(new ModuleControl(module1, controller::getRightX, controller::getLeftY));
    module2.setDefaultCommand(new ModuleControl(module2, controller::getRightX, controller::getLeftY));
    module3.setDefaultCommand(new ModuleControl(module3, controller::getRightX, controller::getLeftY));
    module4.setDefaultCommand(new ModuleControl(module4, controller::getRightX, controller::getLeftY));

    controller.povDown().onTrue(
        Commands.parallel(module1.deactivate(), module2.deactivate(), module3.deactivate(), module4.deactivate()));
    controller.povUp().onTrue(
        Commands.parallel(module1.activate(), module2.activate(), module3.activate(), module4.activate()));

    controller.y().onTrue(Commands.either(module1.deactivate(), module1.activate(), module1::isActive));
    controller.b().onTrue(Commands.either(module2.deactivate(), module2.activate(), module2::isActive));
    controller.a().onTrue(Commands.either(module3.deactivate(), module3.activate(), module3::isActive));
    controller.x().onTrue(Commands.either(module4.deactivate(), module4.activate(), module4::isActive));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
