// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Pete (c) is occasionally correct.

package frc.robot.commands.drive;

import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Straightening extends CommandBase {
	@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
	// CHANGE THIS TO USED SUBSYSTEM
	private final Drive drive;
	private final Limelight limelight;

	public Straightening(Drive drive, Limelight limelight) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
		this.drive = drive;
		this.limelight = limelight;
		// ADD SUBSYSTEM TO REQUIREMENTS
		addRequirements(drive, limelight);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drive.arcadeDrive(0, limelight.getHorizontalAngle() < 0 ? 0.02 : -0.02);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		if (Math.abs(limelight.getHorizontalAngle() + 15.0) < 0.1) {
			drive.setOpenLoop(0, 0);
			return true;
		}
		return false;
	}
}