package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;


public final class Drive extends SubsystemBase {

    private static Drive instance;

    private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
    private final RelativeEncoder leftMasterEncoder, rightMasterEncoder; 
    private final  DriveHelper driveHelper;

    private final ADXRS450_Gyro gyro;
    private final AnalogPotentiometer lightSensor;
    
    private Drive() {
        leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, MotorType.kBrushless);
        leftSlave = new CANSparkMax(DriveConstants.LEFT_SLAVE_PORT, MotorType.kBrushless);
        rightMaster = new CANSparkMax(DriveConstants.RIGHT_MASTER_PORT, MotorType.kBrushless);
        rightSlave = new CANSparkMax(DriveConstants.RIGHT_SLAVE_PORT, MotorType.kBrushless);

        leftMasterEncoder = leftMaster.getEncoder();
        rightMasterEncoder = rightMaster.getEncoder();

        driveHelper = new DriveHelper(leftMaster, rightMaster, leftMasterEncoder, rightMasterEncoder);

        gyro = new ADXRS450_Gyro(DriveConstants.GYRO_PORT);
        lightSensor = new AnalogPotentiometer(DriveConstants.LIGHT_SENSOR_PORT);

        configureSparks();

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
    
    public double[] getMasterRPMs() {
        double[] rpm = {rightMasterEncoder.getVelocity(), leftMasterEncoder.getVelocity()};
        return rpm;
    }

    public double getRightCounts() {
        return rightMasterEncoder.getPosition();
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    public double getLightVoltage() {
        return 100 * lightSensor.get();
    }

    @Override
    public void resetSensors() {
        gyro.reset();
        leftMasterEncoder.setPosition(0.0);
        rightMasterEncoder.setPosition(0.0);
    }
}
