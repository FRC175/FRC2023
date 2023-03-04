package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.arm.RunArm;
import frc.robot.commands.arm.SetArmPosition;
import frc.robot.commands.auto.*;
import frc.robot.commands.drive.Balancing;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.drive.DriveToDist;
import frc.robot.commands.drive.Straightening;
import frc.robot.commands.gripper.Grip;
import frc.robot.commands.led.CycleColor;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pump;
import frc.robot.subsystems.Shuffleboard;
import frc.robot.subsystems.Arm.ArmState;

public class RobotContainer {
	private final Drive drive;
	private final Arm arm;
	private final Gripper gripper;
	private final ColorSensor colorSensor;
	private final LED led;
	private final Shuffleboard shuffleboard;
	private final Limelight limelight;
	private final Pump pump;

	private final XboxController driverController;
	private final XboxController operatorController;
	private final SendableChooser<Command> autoChooser;

	private static RobotContainer instance;

	public RobotContainer() {
		drive = Drive.getInstance();
		arm = Arm.getInstance();
		gripper = Gripper.getInstance();
		colorSensor = ColorSensor.getInstance();
		led = LED.getInstance();
		shuffleboard = Shuffleboard.getInstance();
		limelight = Limelight.getInstance();
		pump = Pump.getInstance();

		driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
		operatorController = new XboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

		autoChooser = new SendableChooser<>();

		CameraServer.startAutomaticCapture();

		configureDefaultCommands();
		configureButtonBindings();
		configureAutoChooser();
	}

	public static RobotContainer getInstance() {
		if (instance == null) {
			instance = new RobotContainer();
		}

		return instance;
	}

	private double deadband(double input) {
		if (Math.abs(input) < 0.1) {
			return 0;
		} else {
			return input;
		}
	}

	private void configureDefaultCommands() {
		// Accel Drive and Lateral Drive
		drive.setDefaultCommand(new RunCommand(() -> {
			double throttle = driverController.getRightTriggerAxis() - driverController.getLeftTriggerAxis();
			double turn = deadband(driverController.getLeftX());
			if (driverController.getAButton()) {
				drive.dampDrive(throttle, turn, 0.5);
			} else if (driverController.getXButton()) {
				drive.dampDrive(throttle, turn, 0.25);
			} else {
				drive.bufferDrive(throttle, turn);
			}
			
		}, drive));

		// Unextends Arm
		arm.setDefaultCommand(new RunCommand(() -> {
			arm.setBrakeOn();
		}, arm));

		// Sends Data to Shuffleboard
		shuffleboard.setDefaultCommand(new RunCommand(() -> {
			shuffleboard.logColorSensor();
			shuffleboard.logDrive();
			shuffleboard.logLimelight();
			shuffleboard.logArm();
			shuffleboard.logGripper();
			shuffleboard.logLED();
		}, shuffleboard));
	}

	private void configureButtonBindings() {
		// Driver B Button: Balance
		new Trigger(() -> driverController.getBButton())
				.onTrue(new Balancing(colorSensor, drive));

		// Driver Y Button: DriveToDist
		new Trigger(() -> driverController.getYButton())
				.onTrue(new DriveToDist(drive, limelight, limelight.pipe == 0 ? FieldConstants.APRIL_GRID : FieldConstants.TAPE, 100));

		// Driver DPAD Down: Straighten
		new Trigger(() -> driverController.getPOV() == 180)
				.onTrue(new Straightening(drive, limelight));

		// Driver Left Claw: Reset Gyro
		new Trigger(() -> driverController.getLeftStickButton())
				.onTrue(new InstantCommand(() -> {
					drive.resetGyro();
				}, drive));

		// Operator Right Stick: Arm and Break
		new Trigger(() -> Math.abs(operatorController.getRightY()) >= 0.10)
				.onTrue(new RunArm(arm, operatorController));

		// Operator Y: Extend Arm
		new Trigger(() -> operatorController.getYButton())
				.whileTrue(new RunCommand(() -> {
					arm.setExtendOn();
				}, arm));

		// Operator X: Unextend Arm
		new Trigger(() -> operatorController.getXButton())
				.whileTrue(new RunCommand(() -> {
					arm.setExtendOff();
				}, arm));

		// Operator RT: Grip Grip Gripper
		new Trigger(() -> operatorController.getRightTriggerAxis() > 0)
				.onTrue(new Grip(gripper, true))
				.onFalse(new Grip(gripper, false));

		// Operator D-Pad Down: Arm to Cube setpoint
		new Trigger(() -> operatorController.getPOV() == 180)
				.onTrue(new SetArmPosition(arm, ArmState.CUBE));

		// Operator D-Pad Right: Arm to Awake setpoint
		new Trigger(() -> operatorController.getPOV() == 90)
				.onTrue(new SetArmPosition(arm, ArmState.AWAKE));

		// Operator D-Pad Left: Arm to Asleep setpoint
		new Trigger(() -> operatorController.getPOV() == 270)
				.onTrue(new SetArmPosition(arm, ArmState.ASLEEP));

		// Operator D-Pad Up: Arm to Portal setpoint
		new Trigger(() -> operatorController.getPOV() == 0)
				.onTrue(new SetArmPosition(arm, ArmState.PORTAL));

		// Operator A: Arm to Middle setpoint
		new Trigger(() -> operatorController.getAButton())
				.onTrue(new SetArmPosition(arm, ArmState.MIDDLE));

		// Operator B: Arm to High setpoint
		new Trigger(() -> operatorController.getBButton())
				.onTrue(new SetArmPosition(arm, ArmState.HIGH));

		// Operator Right Claw: Arm to Reset setpoint
		new Trigger(() -> operatorController.getRightStickButton())
				.onTrue(new InstantCommand(() -> {
					arm.resetSensors();
				}, arm));

		// Operator RB: Cycle LED Right
		new Trigger(() -> operatorController.getRightBumper())
				.onTrue(new CycleColor(led, true));

		// Operator LB: Cycle LED Left
		new Trigger(() -> operatorController.getLeftBumper())
				.onTrue(new CycleColor(led, false));

		// Driver START: Switch Pipes
		new Trigger(() -> driverController.getStartButton())
				.toggleOnTrue(new InstantCommand(() -> {
					limelight.switchPipes();
				}, limelight));
	}

	private void configureAutoChooser() {
		autoChooser.setDefaultOption("Nothing", new WaitCommand(0));
		autoChooser.addOption("Drive Community", new DriveAuto(drive, 70));
		autoChooser.addOption("Auto Balance", new DriveThenBalance(drive, colorSensor));
		autoChooser.addOption("Place Cone Leave", new PlaceConeDriveOut(arm, gripper, drive));

		SmartDashboard.putData(autoChooser);
	}

	public Command getAutonomousCommand() {
		return autoChooser.getSelected();
	}
}
