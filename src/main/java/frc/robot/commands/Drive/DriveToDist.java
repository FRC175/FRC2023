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

	public DriveToDist(Drive drive, Limelight limelight) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
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
		drive.accelDrive(0.4, 0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		if (limelight.pipe == 0) {
			if (limelight.getDistance(FieldConstants.APRIL_GRID) > 95
					&& limelight.getDistance(FieldConstants.APRIL_GRID) < 115) {
				drive.setOpenLoop(0, 0);
				return true;
			}
		} else {
			if (limelight.getDistance(FieldConstants.TAPE) > 95 && limelight.getDistance(FieldConstants.TAPE) < 115) {
				drive.setOpenLoop(0, 0);
				return true;
			}
		}
		return false;
	}
}