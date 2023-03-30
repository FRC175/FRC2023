package frc.robot.commands.auto;

import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.commands.arm.SetArmPosition;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceConeDriveOut extends SequentialCommandGroup {
    public PlaceConeDriveOut(Arm arm, GripperClaw gripper, Drive drive, Telescope telescope) {
        addCommands(
            new InstantCommand(() -> gripper.setGripClosed()),
            new SetArmPosition(arm, ArmState.MIDDLE, true, 0.2),
            // new InstantCommand(() -> telescope.setExtendOn()),
            new WaitCommand(0.4),
            new InstantCommand(() -> gripper.setGripOpen()),
            new WaitCommand(0.4),
            new InstantCommand(() -> gripper.setGripClosed()),
            // new InstantCommand(() -> telescope.setExtendOff()),
            // new SetArmPosition(arm, ArmState.INITIAL, true, 0.2)
            new SetArmPosition(arm, ArmState.INITIAL, true, 0.2),
            new DriveAuto(drive, -90, 0.4)
        );
    }
}