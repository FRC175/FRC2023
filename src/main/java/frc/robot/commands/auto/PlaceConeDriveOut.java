package frc.robot.commands.auto;

import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.commands.arm.SetArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceConeDriveOut extends SequentialCommandGroup {
    public PlaceConeDriveOut(Arm arm, Gripper gripper, Drive drive, Telescope telescope) {
        addCommands(
            new InstantCommand(() -> gripper.setGripClosed()),
            new SetArmPosition(arm, ArmState.HIGH, true),
            new InstantCommand(() -> telescope.setExtendOn()),
            new WaitCommand(0.8),
            new InstantCommand(() -> gripper.setGripOpen()),
            new WaitCommand(0.2),
            new InstantCommand(() -> gripper.setGripOpen()),
            new InstantCommand(() -> telescope.setExtendOff()),
            new SetArmPosition(arm, ArmState.INITIAL, true)
        );
    }
}