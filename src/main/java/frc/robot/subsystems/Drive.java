package frc.robot.subsystems;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;
import com.revrobotics.RelativeEncoder;


public final class Drive extends SubsystemBase {

    // DEFINE MOTORS AND DRIVEHELPER HERE
    
    private static Drive instance;
    
    // private final ADXRS450_Gyro gyro;
    private final TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;
  
    
    private final  DriveHelper driveHelper;
    
    private Drive() {
        // INSTANTIATE MOTORS HERE

        // gyro = new ADXRS450_Gyro(DriveConstants.GYRO_PORT);
        // gyro.calibrate();

        leftMaster = new TalonSRX(DriveConstants.LEFT_MASTER_PORT);
        leftSlave = new TalonSRX(DriveConstants.LEFT_SLAVE_PORT);
        rightMaster = new TalonSRX(DriveConstants.RIGHT_MASTER_PORT);
        rightSlave = new TalonSRX(DriveConstants.RIGHT_SLAVE_PORT);
       
        driveHelper = new DriveHelper(leftMaster, rightMaster);
        
        configureTalons();

        // leftMaster.setOpenLoopRampRate(3.0);
        // leftSlave.setOpenLoopRampRate(3.0);
        // rightMaster.setOpenLoopRampRate(3.0);
        // rightSlave.setOpenLoopRampRate(3.0);

        // PUT MASTER MOTORS INTO DRIVEHELPER

    
        
       
        // does nothing
        resetSensors();
    }

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    //   public double getAngle() {
    //     SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
    //     SmartDashboard.putBoolean("Is connected?", gyro.isConnected());
    //     return gyro.getAngle();
    //  }
     
    //  public void resetGyro() {
    //     gyro.reset();
    // }

    private void configureTalons() {
        // RESTORE FACTORY DEFAULTS, SET INVERTED AND FOLLOWERS
        leftMaster.configFactoryDefault();
        leftMaster.setInverted(false);

        leftSlave.configFactoryDefault();
        leftSlave.follow(leftMaster);
        leftSlave.setInverted(false);
        
        rightMaster.configFactoryDefault();
        rightMaster.setInverted(true);

        rightSlave.configFactoryDefault();
        rightSlave.follow(rightMaster);
        rightSlave.setInverted(true);

        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        leftMaster.configNominalOutputForward(0, 30);
        leftMaster.configNominalOutputReverse(0, 30);
        leftMaster.configPeakOutputForward(1, 30);
        leftMaster.configPeakOutputReverse(-1, 30);

        leftMaster.config_kF(0, 0/*1023.0 / 7200.0*/);     // TODO check this
        leftMaster.config_kP(0, 0);
        leftMaster.config_kI(0, 0);
        leftMaster.config_kD(0, 0);

        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        rightMaster.configNominalOutputForward(0, 30);
        rightMaster.configNominalOutputReverse(0, 30);
        rightMaster.configPeakOutputForward(1, 30);
        rightMaster.configPeakOutputReverse(-1, 30);

        rightMaster.config_kF(0, 0/*1023.0 / 7200.0*/);     // TODO check this
        rightMaster.config_kP(0, 0);
        rightMaster.config_kI(0, 0);
        rightMaster.config_kD(0, 0);
     }

    public void setOpenLoop (double leftDemand, double rightDemand) {
        leftMaster.set(ControlMode.PercentOutput, leftDemand);
        rightMaster.set(ControlMode.PercentOutput, rightDemand);
    }

    
    // PUT DRIVE METHODS HERE (ARCADE, ACCEL)

    public void arcadeDrive (double throttle, double turn) {
        driveHelper.arcadeDrive(throttle, turn);
    }

    PIDController controller = new PIDController(0.0, 1, 0.0);

    public void accelDrive (double throttle, double turn) {
        // controller.setSetpoint(speed);
        // double output = controller.calculate(rightMasterEncoder.getVelocity() / DriveConstants.MAX_RPM);  
        // // double error = (speed * DriveConstants.MAX_RPM) - rightMasterEncoder.getVelocity(); 
        // // double kP = 1.0; 
        // // double output = (rightMasterEncoder.getVelocity() + error * kP) / DriveConstants.MAX_RPM; 
        // setOpenLoop(output, output);
        // SmartDashboard.putNumber("Output", output);

        driveHelper.accelDrive(throttle, turn);
        
        
        // placeholder
    }
    public void parkingDrive () {
        // placeholder
    }
    
    // // public double getRightRPM() {
    //     SmartDashboard.putNumber("Right RPM", rightMasterEncoder.getVelocity());
    //     return rightMasterEncoder.getVelocity();
    // }

    //     public double rightCounts() {
    //         return rightMasterEncoder.getPosition();
    //     }


    @Override
    public void resetSensors() {
        
    }
}
