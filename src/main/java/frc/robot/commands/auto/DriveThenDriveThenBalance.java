package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.Balancing;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;

public class DriveThenDriveThenBalance extends SequentialCommandGroup{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    public DriveThenDriveThenBalance(Drive drive, ColorSensor colorSensor) {
        addCommands(
            new DriveAuto(drive, 90),
            new WaitCommand(1),
            new DriveAuto(drive, -40),
            new Balancing(drive)

        );
    }
}
