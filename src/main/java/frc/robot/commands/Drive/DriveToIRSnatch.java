// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Pete (c) is occasionally correct.

package frc.robot.commands.Drive;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.IRSensor;
import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToIRSnatch extends CommandBase {
	@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
	// CHANGE THIS TO USED SUBSYSTEM
	private final Drive drive;
	private final IRSensor irSensor;
    private final GripperClaw gripper;
    private final LED led;
    private final XboxController controller;

	public DriveToIRSnatch(Drive drive, IRSensor irSensor, GripperClaw gripper, LED led, XboxController controller) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
		this.drive = drive;
		this.irSensor = irSensor;
        this.gripper = gripper;
        this.led = led;
        this.controller = controller;
		// ADD SUBSYSTEM TO REQUIREMENTS
		addRequirements(drive, irSensor, gripper, led);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
        gripper.setGripOpen();
        led.setStripColor(Color.kRed);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drive.arcadeDrive(0.15, 0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
        drive.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		if (irSensor.irDetected() || !controller.getYButton()) {
            gripper.setGripClosed();
            led.setStripColor(Color.kGreen);
            return true;
        } else {
            return false;
        }
	}
}