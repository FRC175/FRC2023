package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import org.opencv.features2d.KAZE;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.DriveHelper;


public final class Drive extends SubsystemBase {

    private static Drive instance;

    private final CANSparkMax leftMaster, leftSlave, rightMaster, rightSlave;
    private final RelativeEncoder leftMasterEncoder, rightMasterEncoder; 
    private final  DriveHelper driveHelper;

    private final TalonSRX latMotor;
    private final DoubleSolenoid latShift;
    private boolean latExtended;

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

        latMotor = new TalonSRX(DriveConstants.LAT_MOTOR_PORT);
        latShift = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, DriveConstants.LAT_FORWARD_CHANNEL, DriveConstants.LAT_REVERSE_CHANNEL);
        latExtended = false;
        extendLat(false);

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

    public void latDrive(double speed) {
        latMotor.set(ControlMode.PercentOutput, speed);
    }

    public void extendLat(boolean extend) {
        latExtended = extend;
        latShift.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public boolean getLatShiftState() {
        return latExtended;
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
