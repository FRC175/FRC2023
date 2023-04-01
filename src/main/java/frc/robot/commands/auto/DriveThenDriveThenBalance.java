package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.Balancing;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;

public class DriveThenDriveThenBalance extends SequentialCommandGroup{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    public DriveThenDriveThenBalance(Drive drive, ColorSensor colorSensor) {
        addCommands(
            new DriveAuto(drive, 90, 0.2),
            new WaitCommand(1),
            new DriveAuto(drive, -40, 0.2),
            new Balancing(drive)

        );
    }
}
