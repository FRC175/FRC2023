package frc.robot.commands.auto;

import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.commands.arm.SetArmPosition;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GripperClaw;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceCone extends SequentialCommandGroup {
    public PlaceCone(Arm arm, GripperClaw gripper, Drive drive, Telescope telescope) {
        addCommands(
            new InstantCommand(() -> gripper.setGripClosed()),
            new SetArmPosition(arm, ArmState.MIDDLE, true, 0.3),
            new DriveAuto(drive, 8, 0.1),
            // new InstantCommand(() -> telescope.setExtendOn()),
            new WaitCommand(0.4),
            new InstantCommand(() -> gripper.setGripOpen()),
            new WaitCommand(0.7),
            new InstantCommand(() -> gripper.setGripClosed()),
            new DriveAuto(drive, -8, 0.1),
            // new InstantCommand(() -> telescope.setExtendOff()),
            // new SetArmPosition(arm, ArmState.INITIAL, true, 0.2)
            new SetArmPosition(arm, ArmState.INITIAL, true, 0.3)
        );
    }
}