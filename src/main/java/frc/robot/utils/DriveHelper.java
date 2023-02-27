package frc.robot.utils;

import java.util.LinkedList;
import java.util.Queue;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants.DriveConstants;

public final class DriveHelper {

	private final CANSparkMax left, right;
	private final RelativeEncoder leftEncoder, rightEncoder;
	private PIDController controllerR, controllerL;
	private Queue<Double> throttleBuffer = new LinkedList<>();
	private final int BUFF_SIZE = 25;
	private double outputThrottle = 0.0;

	public DriveHelper(CANSparkMax left, CANSparkMax right, RelativeEncoder leftEncoder, RelativeEncoder rightEncoder) {
		this.left = left;
		this.right = right;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;

		controllerL = new PIDController(0.0, 1.75, 0.0);
		controllerR = new PIDController(0.0, 1.75, 0.0);
	}

	public void arcadeDrive(double throttle, double turn) {
		double leftOut = throttle + turn;
		double rightOut = throttle - turn;
		left.set(leftOut);
		right.set(rightOut);
	}

	public void dampDrive(double throttle, double turn, double dampness) {
		double leftOut = clamp((throttle + turn), 1.0, -1.0) * dampness;
		double rightOut = clamp((throttle - turn), 1.0, -1.0) * dampness;
		left.set(leftOut);
		right.set(rightOut);
	}

	public void bufferDrive(double throttle, double turn) {
		double staleThrottle = 0.0;
		if (throttleBuffer.size() >= BUFF_SIZE)
            staleThrottle = throttleBuffer.poll();

		throttleBuffer.add(throttle);

		outputThrottle -= (staleThrottle / (double)BUFF_SIZE);
		outputThrottle += throttle / (double)BUFF_SIZE;

		arcadeDrive(outputThrottle, turn);
	}

	private double clamp(double in, double max, double min) {
		if (in > max)
			return max;
		if (in < min)
			return min;
		return in;
	}

	private double applyDeadband(double in, double min) {
		return Math.abs(in) < min ? 0.0 : in;
	}

	public void accelDrive(double throttle, double turn) {
		double goalL = clamp(throttle - turn, 1.0, -1.0);
		double goalR = clamp(throttle + turn, 1.0, -1.0);

		controllerL.setSetpoint(goalL);
		controllerR.setSetpoint(goalR);

		double outputL = applyDeadband(controllerL.calculate(leftEncoder.getVelocity() / DriveConstants.MAX_RPM), 0.05);
		double outputR = applyDeadband(controllerR.calculate(rightEncoder.getVelocity() / DriveConstants.MAX_RPM),
				0.05);

		left.set(outputL);
		right.set(outputR);
	}
}
