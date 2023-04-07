package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.drive.Balancing;

import frc.robot.subsystems.Drive;

public class DriveThenBalanceReverse extends SequentialCommandGroup {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  public DriveThenBalanceReverse(Drive drive) {
    addCommands(
        new DriveAuto(drive, -47, 0.2),
        new WaitCommand(0.5),
        new Balancing(drive)
        );
  }
}
