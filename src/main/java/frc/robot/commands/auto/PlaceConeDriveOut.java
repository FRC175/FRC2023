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
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceConeDriveOut extends SequentialCommandGroup {
    public PlaceConeDriveOut(Arm arm, Gripper gripper, Drive drive) {
        addCommands(
            new Grip(gripper, true),
            new SetArmPosition(arm, ArmState.HIGH, true),
            new InstantCommand(() -> arm.setExtendOn()),
            new WaitCommand(0.8),
            new Grip(gripper, false),
            new WaitCommand(0.2),
            new DriveAuto(drive, -90)
            // new DriveAuto(drive, 40)
        );
    }
}