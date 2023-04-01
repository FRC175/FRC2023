package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.Balancing;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;

public class DriveThenBalanceReverse extends SequentialCommandGroup {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  public DriveThenBalanceReverse(Drive drive, ColorSensor colorSensor) {
    addCommands(
        new DriveAuto(drive, -55, 0.2),
        new WaitCommand(1),
        new Balancing(drive)
        );
  }
}
