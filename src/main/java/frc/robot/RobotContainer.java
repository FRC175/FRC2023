package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.arm.RunArm;
import frc.robot.commands.auto.*;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.commands.Drive.DriveToGoal;
import frc.robot.commands.Drive.DriveToIRSnatch;
import frc.robot.commands.Drive.Straightening;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.commands.Drive.DriveToGoal;
import frc.robot.commands.Drive.DriveToIRSnatch;
import frc.robot.commands.Drive.Straightening;
import frc.robot.commands.led.CycleColor;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GripperClaw;
import frc.robot.subsystems.IRSensor;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shuffleboard;
import frc.robot.subsystems.Telescope;

public class RobotContainer {
	private final Drive drive;
	private final Arm arm;
	private final GripperClaw gripperClaw;
	private final LED led;
	private final Shuffleboard shuffleboard;
	private final Limelight limelight;
	private final IRSensor irSensor;
	private final Telescope telescope;

	private final XboxController driverController;
	private final XboxController operatorController;
	private final SendableChooser<Command> autoChooser;

	private final UsbCamera usbCamera;

	private static RobotContainer instance;

	public RobotContainer() {
		drive = Drive.getInstance();
		arm = Arm.getInstance();
		gripperClaw = GripperClaw.getInstance();
		led = LED.getInstance();
		shuffleboard = Shuffleboard.getInstance();
		limelight = Limelight.getInstance();
		telescope = Telescope.getInstance();
		irSensor = IRSensor.getInstance();

		usbCamera = CameraServer.startAutomaticCapture(1);

		driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
		operatorController = new XboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

		autoChooser = new SendableChooser<>();

		CameraServer.startAutomaticCapture();
		CameraServerSharedStore.getCameraServerShared().reportUsbCamera(usbCamera.getHandle());

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
				drive.bufferDrive(throttle, turn);
			} else if (driverController.getXButton()) {
				drive.setOpenLoop(0.25, 0.25);
			} else if (driverController.getYButton()) {
				drive.setOpenLoop(0.1,0.1);
			} else {
				drive.dampDrive(throttle, turn, 0.5);
			}
			
		}, drive));

		// Unextends Arm
		arm.setDefaultCommand(new RunArm(arm, operatorController));

		// Sends Data to Shuffleboard
		shuffleboard.setDefaultCommand(new RunCommand(() -> {
			shuffleboard.logColorSensor();
			shuffleboard.logDrive();
			shuffleboard.logLimelight();
			shuffleboard.logArm();
			shuffleboard.logGripper();
			shuffleboard.logLED();
			shuffleboard.logIR();
		}, shuffleboard));
	}

	private void configureButtonBindings() {
		// Driver B Button: Balance
		// new Trigger(() -> driverController.getBButton())
		// 		.onTrue(new Balancing(drive));

		// Driver Y Button: DriveToDist
		new Trigger(() -> driverController
		.getYButton())
				// .onTrue(new DriveToIRSnatch(drive, irSensor, gripperClaw, led, driverController));
				.onTrue(new DriveToGoal(drive, () -> irSensor.irDetected(), 0.2, true));

		// Driver DPAD Down: Straighten
		// new Trigger(() -> driverController.getPOV() == 180)
		// 		.onTrue(new Straightening(drive, limelight));

		// Driver Left Claw: Reset Gyro
		new Trigger(() -> driverController.getLeftStickButton())
				.onTrue(new InstantCommand(() -> {
					drive.resetGyro();
				}, drive));

		// Operator Right Stick: Arm and Break
		// new Trigger(() -> Math.abs(operatorController.getRightY()) >= 0.10)
		// 		.onTrue(new RunArm(arm, operatorController));

		// Operator Y: Extend Arm
		new Trigger(() -> operatorController.getYButton())
				.whileTrue(new RunCommand(() -> {
					telescope.setExtendOn();
				}, arm));

		// Operator X: Unextend Arm
		new Trigger(() -> operatorController.getXButton())
				.whileTrue(new RunCommand(() -> {
					telescope.setExtendOff();
				}, arm));
		// Operator dpad up: grip Gripper Motors In
		new Trigger(() -> operatorController.getPOV() == 0)
				.onTrue(new InstantCommand(() -> {
					gripperClaw.setOpenLoop(.5);
				}, gripperClaw))
				.onFalse(new InstantCommand(() -> {
					gripperClaw.setOpenLoop(0.0);
				}));
		//Operator dpad down: grip Gripper Motors Out
		new Trigger(() -> operatorController.getPOV() == 180)
				.onTrue(new InstantCommand(() -> {
					gripperClaw.setOpenLoop(-0.5);
				}, gripperClaw))
				.onFalse(new InstantCommand(() -> {
					gripperClaw.setOpenLoop(0);
				})); 

		// Operator RT: Grip Grip Gripper
		new Trigger(() -> operatorController.getRightTriggerAxis() > 0)
				.onTrue(new InstantCommand(() -> {
					gripperClaw.setGripOpen();
				}, gripperClaw))
				.onFalse(new InstantCommand(() -> {
					gripperClaw.setGripClosed();
				}, gripperClaw));
		
	
		
		
		
		

		// Operator D-Pad Down: Arm to Cube setpoint
		// new Trigger(() -> operatorController.getPOV() == 180)
		// 		.onTrue(new SetArmPosition(arm, ArmState.CUBE, false));

		// Operator D-Pad Right: Arm to Awake setpoint
		// new Trigger(() -> operatorController.getPOV() == 90)
		// 		.onTrue(new SetArmPosition(arm, ArmState.AWAKE, false));

		// Operator D-Pad Left: Arm to Asleep setpoint
		// new Trigger(() -> operatorController.getPOV() == 270)
		// 		.onTrue(new SetArmPosition(arm, ArmState.ASLEEP, false));

		// Operator D-Pad Up: Arm to Portal setpoint
		// new Trigger(() -> operatorController.getPOV() == 0)
		// 		.whileTrue(new SetArmPosition(arm, ArmState.PORTAL, false));

		// Operator A: Arm to Middle setpoint
		// new Trigger(() -> operatorController.getAButton())
		// 		.onTrue(new SetArmPosition(arm, ArmState.MIDDLE, false));

		// Operator B: Arm to High setpoint
		// new Trigger(() -> operatorController.getBButton())
		// 		.onTrue(new SetArmPosition(arm, ArmState.HIGH, false));

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
		autoChooser.addOption("Drive Away", new DriveAuto(drive, 90, 0.4));
		autoChooser.addOption("Drive to Balance", new DriveThenBalance(drive));
		autoChooser.addOption("Drive to Balance Backward", new DriveThenBalanceReverse(drive));
		autoChooser.addOption("Place Cone", new PlaceCone(arm, gripperClaw, drive, telescope));
		autoChooser.addOption("Place Cone Leave", new PlaceConeDriveOut(arm, gripperClaw, drive, telescope));
		autoChooser.addOption("Drive Out then Balance", new DriveThenDriveThenBalance(drive));
		autoChooser.addOption("Drive Out then Balance Reverse", new DriveThenDriveThenBalanceReverse(drive));
		autoChooser.addOption("God Mode", new GodMode(drive, arm, telescope, gripperClaw));
		autoChooser.addOption("God Mode for Wimps", new GodModeForBabies(drive, arm, telescope, gripperClaw));

		SmartDashboard.putData(autoChooser);
	}

	public Command getAutonomousCommand() {
		return autoChooser.getSelected();
	}
}
