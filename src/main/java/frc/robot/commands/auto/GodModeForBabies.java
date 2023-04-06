package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.Telescope;

public class GodModeForBabies extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public GodModeForBabies(Drive drive, Arm arm, Telescope telescope, GripperClaw gripper) {
        addCommands(
            new DriveAuto(drive, 7, 0.1),
            new PlaceCone(arm, gripper, drive, telescope)
            // new DriveThenBalanceReverse(drive)
        );
    }
}
