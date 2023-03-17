package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.FieldConstants;

public class Shuffleboard extends SubsystemBase {

	private static Shuffleboard instance;

	private Drive drive;
	private ColorSensor colorSensor;
	private Limelight limelight;
	private Arm arm;
	private Gripper gripper;
	private LED led;
	private Telescope telescope;
	private IRSensor irSensor;

	private Shuffleboard() {
		drive = Drive.getInstance();
		colorSensor = ColorSensor.getInstance();
		limelight = Limelight.getInstance();
		arm = Arm.getInstance();
		gripper = Gripper.getInstance();
		led = LED.getInstance();
		telescope = Telescope.getInstance();
		irSensor = IRSensor.getInstance();
	}

	public static Shuffleboard getInstance() {
		if (instance == null) {
			instance = new Shuffleboard();
		}

		return instance;
	}

	public void logDrive() {
		// SmartDashboard.putNumber("Right Master Velocity", drive.getMasterRPMs()[0]);
		// SmartDashboard.putNumber("Left Master Velocity", drive.getMasterRPMs()[1]);

		SmartDashboard.putNumber("Gyro Angle", drive.getAngle());
	}

	public void logColorSensor() {
		// SmartDashboard.putBoolean("Is RB?", colorSensor.determineRB());

		// SmartDashboard.putString("Color", "r: " + colorSensor.getColor().red + " g: " + colorSensor.getColor().green + " b: " + colorSensor.getColor().blue);
	}

	public void logArm() {
		SmartDashboard.putBoolean("Is Telescope?", telescope.getTeleShiftState());
		SmartDashboard.putNumber("Arm Encoder Value", arm.getEncoderCount());
		// SmartDashboard.putString("Arm Setpoint", arm.getArmState().name());
		SmartDashboard.putBoolean("Brake", arm.getBrakeState());
	}

	public void logGripper() {
		SmartDashboard.putBoolean("Is Gripping?", gripper.getGripShiftState());
	}

	public void logIR() {
		SmartDashboard.putBoolean("IR Got", irSensor.irDetected());
		SmartDashboard.putNumber("IR Reading", irSensor.getIRValue());
	}

	public void logLimelight() {
		// SmartDashboard.putNumber("Distance To Grid",
		// 		limelight.getDistance(limelight.pipe == 0 ? FieldConstants.APRIL_GRID : FieldConstants.TAPE));
		// SmartDashboard.putNumber("Pipe", limelight.pipe);
	}

	public void logLED() {
		SmartDashboard.putString("LED Color", led.getColor().toString());
	}

	@Override
	public void resetSensors() {

	}
}