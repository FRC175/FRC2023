// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Pete (c) is occasionally correct.

package frc.robot.commands.drive;

import frc.robot.subsystems.Drive;
import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToGoal extends CommandBase {
	@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
	// CHANGE THIS TO USED SUBSYSTEM
	private final Drive drive;
    private final BooleanSupplier goal;
    private double speed;
    private boolean forward;

	public DriveToGoal(Drive drive, BooleanSupplier supplier, double speed, boolean forward) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
		this.drive = drive;
		this.goal = supplier;
        this.speed = speed;
        this.forward = forward;
		// ADD SUBSYSTEM TO REQUIREMENTS
		addRequirements(drive);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
        
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drive.arcadeDrive(forward ? speed : -speed, 0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
        drive.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		if (goal.getAsBoolean()) {
            return true;
        } else {
            return false;
        }
	}
}