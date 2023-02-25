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
        // SmartDashboard.putNumber("Light Level (V)", drive.getLightVoltage());

        SmartDashboard.putBoolean("Is Lat?", drive.getLatShiftState());
    }

    public void logColorSensor() {
        // SmartDashboard.putString("Current Color (rgb)", "r: " + colorSensor.getColor().red + " g: " + colorSensor.getColor().green + " b: " + colorSensor.getColor().blue);
        SmartDashboard.putBoolean("Is RB?", colorSensor.determineRB());
        // SmartDashboard.putBoolean("Is Cone?", colorSensor.determineGP() == GamePiece.CONE);
        // SmartDashboard.putBoolean("Is Cube?", colorSensor.determineGP() == GamePiece.CUBE);
    }

    public void logArm() {
        SmartDashboard.putBoolean("Is Telescope?", arm.getTeleShiftState());
        SmartDashboard.putNumber("Encoder Value", arm.getEncoderCount());
        SmartDashboard.putString("Arm Setpoint", arm.getArmState().name());
    } 
    
    public void logGripper() {
        SmartDashboard.putBoolean("Is Gripping?", gripper.getGripShiftState());
    }

    public void logLimelight() {
        SmartDashboard.putNumber("Distance To Grid", limelight.getDistance(limelight.pipe == 0 ? FieldConstants.APRIL_GRID : FieldConstants.TAPE));
        SmartDashboard.putNumber("Pipe", limelight.pipe);
    }

    public void logLED() {
        SmartDashboard.putNumber("LED Color", led.getColor());
    }

    @Override
    public void resetSensors() {
        
    } 
}