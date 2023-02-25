package frc.robot.utils;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants.DriveConstants;

public final class DriveHelper {

    private final CANSparkMax left, right;
    private final RelativeEncoder leftEncoder, rightEncoder;
    PIDController controllerR, controllerL;
    
    public DriveHelper(CANSparkMax left, CANSparkMax right, RelativeEncoder leftEncoder, RelativeEncoder rightEncoder) {
        this.left = left;
        this.right = right;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;

        controllerL = new PIDController(0.0, 1.0, 0.0);
        controllerR = new PIDController(0.0, 1.0, 0.0);
    }

    public void arcadeDrive(double throttle, double turn) {
       double leftOut = throttle - turn; 
       double rightOut = throttle + turn; 
       left.set(leftOut);
       right.set(rightOut);
    }
    
    private double clamp(double in, double max, double min) {
        if (in > max) return max;
        if (in < min) return min;
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
        double outputR = applyDeadband(controllerR.calculate(rightEncoder.getVelocity() / DriveConstants.MAX_RPM), 0.05);

        left.set(outputL);
        right.set(outputR);
    }
}
