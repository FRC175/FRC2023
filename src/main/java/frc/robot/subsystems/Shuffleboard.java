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

	private Shuffleboard() {
		drive = Drive.getInstance();
		colorSensor = ColorSensor.getInstance();
		limelight = Limelight.getInstance();
		arm = Arm.getInstance();
		gripper = Gripper.getInstance();
		led = LED.getInstance();
	}

	public static Shuffleboard getInstance() {
		if (instance == null) {
			instance = new Shuffleboard();
		}

		return instance;
	}

	public void logDrive() {
		SmartDashboard.putNumber("Right Master Velocity", drive.getMasterRPMs()[0]);
		SmartDashboard.putNumber("Left Master Velocity", drive.getMasterRPMs()[1]);

		SmartDashboard.putNumber("Gyro Angle", drive.getAngle());
	}

	public void logColorSensor() {
		SmartDashboard.putBoolean("Is RB?", colorSensor.determineRB());
	}

	public void logArm() {
		SmartDashboard.putBoolean("Is Telescope?", arm.getTeleShiftState());
		SmartDashboard.putNumber("Arm Encoder Value", arm.getEncoderCount());
		SmartDashboard.putString("Arm Setpoint", arm.getArmState().name());
	}

	public void logGripper() {
		SmartDashboard.putBoolean("Is Gripping?", gripper.getGripShiftState());
	}

	public void logLimelight() {
		SmartDashboard.putNumber("Distance To Grid",
				limelight.getDistance(limelight.pipe == 0 ? FieldConstants.APRIL_GRID : FieldConstants.TAPE));
		SmartDashboard.putNumber("Pipe", limelight.pipe);
	}

	public void logLED() {
		SmartDashboard.putNumber("LED Color", led.getColor());
	}

	@Override
	public void resetSensors() {

	}
}