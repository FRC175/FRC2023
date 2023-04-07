package frc.robot.commands.auto;

import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Telescope;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.led.RainbowCycle;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceConeDriveOut extends SequentialCommandGroup {
    public PlaceConeDriveOut(Arm arm, GripperClaw gripper, Drive drive, Telescope telescope, LED led) {
        addCommands(
            new PlaceCone(arm, gripper, drive, telescope),
            new DriveAuto(drive, -90, 0.4),
            new RainbowCycle(led, 1000)
        );
    }
}