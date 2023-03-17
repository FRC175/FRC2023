package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;

public final class Drive extends SubsystemBase {

	private static Drive instance;

	private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
	private final RelativeEncoder leftMasterEncoder, rightMasterEncoder;
	private final DriveHelper driveHelper;

	private final ADXRS450_Gyro gyro;

	private Drive() {
		leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, MotorType.kBrushless);
		leftSlave = new CANSparkMax(DriveConstants.LEFT_SLAVE_PORT, MotorType.kBrushless);
		rightMaster = new CANSparkMax(DriveConstants.RIGHT_MASTER_PORT, MotorType.kBrushless);
		rightSlave = new CANSparkMax(DriveConstants.RIGHT_SLAVE_PORT, MotorType.kBrushless);

		leftMasterEncoder = leftMaster.getEncoder();
		rightMasterEncoder = rightMaster.getEncoder();

		driveHelper = new DriveHelper(leftMaster, rightMaster, leftMasterEncoder, rightMasterEncoder);

		gyro = new ADXRS450_Gyro(DriveConstants.GYRO_PORT);

		configureSparks();

		resetGyro();

		resetSensors();
	}

	public static Drive getInstance() {
		if (instance == null) {
			instance = new Drive();
		}

		return instance;
	}

	private void configureSparks() {
		leftMaster.restoreFactoryDefaults();
		leftMaster.setInverted(true);

		leftSlave.restoreFactoryDefaults();
		leftSlave.follow(leftMaster);
		leftSlave.setInverted(true);

		rightMaster.restoreFactoryDefaults();
		rightMaster.setInverted(false);

		rightSlave.restoreFactoryDefaults();
		rightSlave.follow(rightMaster);
		rightSlave.setInverted(false);
	}

	public void setOpenLoop(double leftDemand, double rightDemand) {
		leftMaster.set(leftDemand);
		rightMaster.set(rightDemand);
	}

	public void arcadeDrive(double throttle, double turn) {
		driveHelper.arcadeDrive(throttle, turn);
	}

	public void accelDrive(double throttle, double turn) {
		driveHelper.accelDrive(throttle, turn);
	}

	public void dampDrive(double throttle, double turn, double dampness) {
		driveHelper.dampDrive(throttle, turn, dampness);
	}

	public void bufferDrive(double throttle, double turn) {
		driveHelper.bufferDrive(throttle, turn);
	}

	public double[] getMasterRPMs() {
		double[] rpm = { rightMasterEncoder.getVelocity(), leftMasterEncoder.getVelocity() };
		return rpm;
	}

	public double[] getMasterPositions() {
		double[] pos = { rightMasterEncoder.getPosition(), leftMasterEncoder.getPosition() };
		return pos;
	}

	public double getAngle() {
		return gyro.getAngle();
	}

	public void resetGyro() {
		gyro.reset();
	}

	@Override
	public void resetSensors() {
		leftMasterEncoder.setPosition(0.0);
		rightMasterEncoder.setPosition(0.0);
	}
}
