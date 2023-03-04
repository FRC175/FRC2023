package frc.robot.commands.auto;

import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.commands.arm.SetArmPosition;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.gripper.Grip;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceConeDriveOut extends SequentialCommandGroup {
    public PlaceConeDriveOut(Arm arm, Gripper gripper, Drive drive) {
        addCommands(
            new Grip(gripper, true),
            new SetArmPosition(arm, ArmState.HIGH),
            new InstantCommand(() -> arm.setExtendOn()),
            new Grip(gripper, false),
            new DriveAuto(drive, 40)
        );
    }
}