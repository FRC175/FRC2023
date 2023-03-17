package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.Balancing;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;

public class DriveThenDriveThenBalanceReverse extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    public DriveThenDriveThenBalanceReverse(Drive drive, ColorSensor colorSensor) {
        addCommands(
            new DriveAuto(drive, -95, 0.2),
            new WaitCommand(0.1),
            new DriveAuto(drive, 34, 0.2),
            new Balancing(drive)
        );
    }
}