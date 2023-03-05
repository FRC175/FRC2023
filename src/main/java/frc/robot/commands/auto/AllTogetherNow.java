package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Telescope;

public class AllTogetherNow extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public AllTogetherNow(Drive drive, ColorSensor colorSensor, Arm arm, Telescope telescope, Gripper gripper) {
        addCommands(
            new PlaceConeDriveOut(arm, gripper, drive, telescope),
            new DriveThenDriveThenBalanceReverse(drive, colorSensor)
        );
    }
}
