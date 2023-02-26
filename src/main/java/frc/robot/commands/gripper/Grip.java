package frc.robot.commands.gripper;

import frc.robot.subsystems.Gripper;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Grip extends CommandBase {
	@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

	private final Gripper gripper;
	private boolean grip;

	public Grip(Gripper gripper, boolean grip) {
		this.gripper = gripper;
		this.grip = grip;
		addRequirements(gripper);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		gripper.gripped(grip);
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
