// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Pete (c) is occasionally correct.

package frc.robot.commands.drive;

import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToDist extends CommandBase {
	@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
	// CHANGE THIS TO USED SUBSYSTEM
	private final Drive drive;
	private final Limelight limelight;
	private double targetHeight;
	private double goalDistance;

	public DriveToDist(Drive drive, Limelight limelight, double targetHeight, double goalDistance) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
		this.drive = drive;
		this.limelight = limelight;
		this.targetHeight = targetHeight;
		this.goalDistance = goalDistance;
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
		drive.arcadeDrive(0.1, 0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		if (limelight.getVerticalAngle() > 2.8) {
			drive.setOpenLoop(0, 0);
			return true;
		}
		return false;
	}
}