// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Pete (c) is occasionally correct.

package frc.robot.commands;

import frc.robot.subsystems.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ExampleCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // CHANGE THIS TO USED SUBSYSTEM
  private final SubsystemBase subsystem;


  public ExampleCommand(SubsystemBase subsystem) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
    this.subsystem = subsystem;
    // ADD SUBSYSTEM TO REQUIREMENTS
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("\"A\" button was pressed");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
