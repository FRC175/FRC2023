package frc.robot.subsystems;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Constants.DriveConstants;

public final class Drive extends SubsystemBase {

    // DEFINE MOTORS AND DRIVEHELPER HERE
    
    private static Drive instance;
    
    private final ADXRS450_Gyro gyro;
    private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
    private final  DriveHelper driveHelper;
    
    private Drive() {
        // INSTANTIATE MOTORS HERE

        gyro = new ADXRS450_Gyro(DriveConstants.GYRO_PORT);
        gyro.calibrate();

        leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, MotorType.kBrushless);
        leftSlave = new CANSparkMax(DriveConstants.LEFT_SLAVE_PORT, MotorType.kBrushless);
        rightMaster = new CANSparkMax(DriveConstants.RIGHT_MASTER_PORT, MotorType.kBrushless);
        rightSlave = new CANSparkMax(DriveConstants.RIGHT_SLAVE_PORT, MotorType.kBrushless);
        driveHelper = new DriveHelper(leftMaster, rightMaster);
        
       

        

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
    public void accelDrive () {
        // placeholder
    }
    public void parkingDrive () {
        // placeholder
    }
    
    @Override
    public void resetSensors() {
        
    }
}
