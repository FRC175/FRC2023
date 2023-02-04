package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;
import com.revrobotics.RelativeEncoder;

public final class Drive extends SubsystemBase {

    // DEFINE MOTORS AND DRIVEHELPER HERE
    
    private static Drive instance;
    
    private final ADXRS450_Gyro gyro;
    private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
    private final RelativeEncoder leftMasterEncoder, rightMasterEncoder, leftSlaveEncoder, rightSlaveEncoder; 
    private final  DriveHelper driveHelper;
    
    private Drive() {
        // INSTANTIATE MOTORS HERE

        gyro = new ADXRS450_Gyro(DriveConstants.GYRO_PORT);
        gyro.calibrate();

        leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, MotorType.kBrushless);
        leftSlave = new CANSparkMax(DriveConstants.LEFT_SLAVE_PORT, MotorType.kBrushless);
        rightMaster = new CANSparkMax(DriveConstants.RIGHT_MASTER_PORT, MotorType.kBrushless);
        rightSlave = new CANSparkMax(DriveConstants.RIGHT_SLAVE_PORT, MotorType.kBrushless);

        leftMasterEncoder = leftMaster.getEncoder();
        leftSlaveEncoder = leftSlave.getEncoder(); 
        rightMasterEncoder = rightMaster.getEncoder();
        rightSlaveEncoder = rightSlave.getEncoder();
        driveHelper = new DriveHelper(leftMaster, rightMaster, leftMasterEncoder, rightMasterEncoder);

        // leftMaster.setOpenLoopRampRate(3.0);
        // leftSlave.setOpenLoopRampRate(3.0);
        // rightMaster.setOpenLoopRampRate(3.0);
        // rightSlave.setOpenLoopRampRate(3.0);

        // PUT MASTER MOTORS INTO DRIVEHELPER

        configureSparks();
        
       
        // does nothing
        resetSensors();
    }

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

      public double getAngle() {
        SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
        SmartDashboard.putBoolean("Is connected?", gyro.isConnected());
        return gyro.getAngle();
     }

    private void configureSparks() {
        // RESTORE FACTORY DEFAULTS, SET INVERTED AND FOLLOWERS
        leftMaster.restoreFactoryDefaults();
        leftMaster.setInverted(false);
        
        leftSlave.restoreFactoryDefaults();
        leftSlave.follow(leftMaster);
        leftSlave.setInverted(false);
        
        rightMaster.restoreFactoryDefaults();
        rightMaster.setInverted(true);

        rightSlave.restoreFactoryDefaults();
        rightSlave.follow(rightMaster);
        rightSlave.setInverted(true);

    }

    public void setOpenLoop (double leftDemand, double rightDemand) {
        leftMaster.set(leftDemand);
        rightMaster.set(rightDemand);
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
    
    public double getRightRPM() {
        SmartDashboard.putNumber("Right RPM", rightMasterEncoder.getVelocity());
        return rightMasterEncoder.getVelocity();
    }

        public double rightCounts() {
            return rightMasterEncoder.getPosition();
        }


    @Override
    public void resetSensors() {
        
    }
}
