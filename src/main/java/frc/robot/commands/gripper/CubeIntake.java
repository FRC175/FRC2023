package frc.robot.commands.gripper;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.GripperClaw;

public class CubeIntake extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public CubeIntake(GripperClaw gripper) {
        addCommands(
            new InstantCommand(() -> {
                gripper.setGripClosed();
            }, gripper),
            new InstantCommand(() -> {
                gripper.setOpenLoop(0.25);
            }, gripper),
            new WaitCommand(0.05),
            new InstantCommand(() -> {
                gripper.setOpenLoop(0);
            }, gripper)
        );
    }
}
