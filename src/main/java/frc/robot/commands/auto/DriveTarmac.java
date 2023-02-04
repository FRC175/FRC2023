// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class DriveTarmac extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  public DriveTarmac(Drive drive) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
    addCommands(
      new DriveAuto(drive, 70)
    );
  }

  }

