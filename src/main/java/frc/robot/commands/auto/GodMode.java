package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.Telescope;

public class GodMode extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public GodMode(Drive drive, Arm arm, Telescope telescope, GripperClaw gripper) {
        addCommands(
            new PlaceCone(arm, gripper, drive, telescope),
            new DriveThenDriveThenBalanceReverse(drive)
        );
    }
}
