package frc.robot.utils;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import java.util.LinkedList;
import java.util.Queue;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;

public final class DriveHelper {
    
    
    // Spark Maxes
    private final TalonSRX left, right;
    PIDController controllerR, controllerL;
    Queue<Double> driveLeftBuffer = new LinkedList<Double>();
    Queue<Double> driveRightBuffer = new LinkedList<Double>();
    double outputL = 0.0;
    double outputR = 0.0;
    double BUFFER_SIZE = 10;

    /**
     * Constructs a new DriveHelper.
     *
     * @param left  The master Spark Max for the left drive mots
     */
    public DriveHelper(TalonSRX left, TalonSRX right) {
        this.left = left;
        this.right = right;
      
        controllerL = new PIDController(0.0, 1.0, 0.0);
        controllerR = new PIDController(0.0, 1.0, 0.0);

    }

    // BASIC ARCADE DRIVE (PUT THE MATH IN)
    public void arcadeDrive(double throttle, double turn) {
       double leftOut = throttle - turn; 
       double rightOut = throttle + turn; 
       left.set(ControlMode.PercentOutput, leftOut);
       right.set(ControlMode.PercentOutput, rightOut); 
           // MATH
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
       
        double staleL = 0.0;
        double staleR = 0.0;

        if (driveLeftBuffer.size() >= BUFFER_SIZE)
            staleL = driveLeftBuffer.poll();
        if (driveRightBuffer.size() >= BUFFER_SIZE) 
            staleR = driveRightBuffer.poll();

       double goalL = clamp(throttle - turn, 1.0, -1.0);
       double goalR = clamp(throttle + turn, 1.0, -1.0);

       driveLeftBuffer.add(goalL);
       driveRightBuffer.add(goalR);
       
       outputL -= (staleL / (double)BUFFER_SIZE);
       outputR -= (staleR / (double)BUFFER_SIZE);
       
       outputL += goalL / (double)BUFFER_SIZE;
       outputR += goalR / (double)BUFFER_SIZE;

       //controllerL.setSetpoint(goalL);
       //controllerR.setSetpoint(goalR);

       //double outputL = applyDeadband(controllerL.calculate(leftMasterEncoder.getVelocity() / DriveConstants.MAX_RPM), 0.05);
       //double outputR = applyDeadband(controllerR.calculate(rightMasterEncoder.getVelocity() / DriveConstants.MAX_RPM), 0.05);

        // left.set(ControlMode.PercentOutput, outputL);
        // right.set(ControlMode.PercentOutput, outputR);
        left.set(ControlMode.PercentOutput, outputL);
        right.set(ControlMode.PercentOutput, outputR);
        // SmartDashboard.putNumber("OutputR", right.getMotorOutputPercent());
        // SmartDashboard.putNumber("OutputL", left.getMotorOutputPercent());
        SmartDashboard.putNumber("GoalR", outputL);
        SmartDashboard.putNumber("GoalL", outputR);
        // SmartDashboard.putNumber("Right RPM", rightMasterEncoder.getVelocity());
        // SmartDashboard.putNumber("Left RPM", leftMasterEncoder.getVelocity()); 
        
    }
}
