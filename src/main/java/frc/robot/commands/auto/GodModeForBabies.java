package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.led.RainbowCycle;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Telescope;

public class GodModeForBabies extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public GodModeForBabies(Drive drive, Arm arm, Telescope telescope, GripperClaw gripper, LED led) {
        addCommands(
            new PlaceCone(arm, gripper, drive, telescope),
            new DriveThenBalanceReverse(drive),
            new RainbowCycle(led, 1000)
        );
    }
}
