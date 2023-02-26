package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.Balancing;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;

public class DriveThenBalance extends SequentialCommandGroup {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  public DriveThenBalance(Drive drive, ColorSensor colorSensor) {
    addCommands(
        new DriveAuto(drive, 70),
        new Balancing(colorSensor, drive));
  }
}
