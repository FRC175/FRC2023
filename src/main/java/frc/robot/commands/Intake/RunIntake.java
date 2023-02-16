// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class RunIntake extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // CHANGE THIS TO USED SUBSYSTEM
  private final Intake intake;
  private double demand; 



  public RunIntake(Intake intake, double demand) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
    this.intake = intake;
    this.demand = demand; 
    // ADD SUBSYSTEM TO REQUIREMENTS
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.setRunOpenLoop(demand);
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