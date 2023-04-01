package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.Balancing;
import frc.robot.commands.drive.DriveAuto;

import frc.robot.subsystems.Drive;

public class DriveThenBalance extends SequentialCommandGroup {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  public DriveThenBalance(Drive drive) {
    addCommands(
        new DriveAuto(drive, 55, 0.2),
        new WaitCommand(1),
        new Balancing(drive)
        );
  }
}
