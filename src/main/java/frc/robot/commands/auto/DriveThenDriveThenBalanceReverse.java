package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.Drive.Balancing;
import frc.robot.commands.Drive.DriveAuto;


import frc.robot.commands.Drive.Balancing;
import frc.robot.commands.Drive.DriveAuto;

import frc.robot.subsystems.Drive;

public class DriveThenDriveThenBalanceReverse extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    public DriveThenDriveThenBalanceReverse(Drive drive) {
        addCommands(
            new DriveAuto(drive, -96, 0.3),
            new WaitCommand(0.1),
            new DriveAuto(drive, 35, 0.2),
            new Balancing(drive)
        );
    }
}