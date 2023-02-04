package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.FieldConstants;
import frc.robot.Constants.LimelightConstants;

public class Shuffleboard extends SubsystemBase {

    private static Shuffleboard instance;

    private Drive drive;
    private ColorSensor colorSensor;
    private Limelight limelight;
    private Arm arm;
    

    private Shuffleboard() {
        drive = Drive.getInstance();
        colorSensor = ColorSensor.getInstance();
        limelight = Limelight.getInstance();
        arm = Arm.getInstance();
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
        SmartDashboard.putNumber("Light Level (V)", drive.getLightVoltage());

        SmartDashboard.putBoolean("Is Lat?", drive.getLatShiftState());
    }

    public void logColorSensor() {
        SmartDashboard.putString("Current Color (rgb)", "r: " + colorSensor.getColor().red + " g: " + colorSensor.getColor().green + " b: " + colorSensor.getColor().blue);
        SmartDashboard.putBoolean("Is RB?", colorSensor.determineRB());
        SmartDashboard.putBoolean("Is Cone?", colorSensor.determineGP() == 'y');
        SmartDashboard.putBoolean("Is Cube?", colorSensor.determineGP() == 'p');
    }

    public void logArm() {
        SmartDashboard.putBoolean("Is Telescope?", arm.getTeleShiftState());
    }

    public void logLimelight() {
        SmartDashboard.putNumber("Distance", limelight.getDistance(limelight.pipe == 0 ? FieldConstants.APRIL_TAPE : FieldConstants.BREAD_TAPE));
        SmartDashboard.putNumber("Pipe", limelight.pipe);
    }

    @Override
    public void resetSensors() {
        
    } 
}