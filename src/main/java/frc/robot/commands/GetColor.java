// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ColorSensor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;




public class GetColor extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // CHANGE THIS TO USED SUBSYSTEM
  private final ColorSensor colorSensor;

  public GetColor(ColorSensor colorSensor) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
    this.colorSensor = colorSensor;
    // ADD SUBSYSTEM TO REQUIREMENTS
    addRequirements(colorSensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println("\"B\" button was pressed");
    System.out.println(colorSensor.determineColor());
    SmartDashboard.putBoolean("Is Red?", colorSensor.determineColor());
    
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
