package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.FieldConstants;
import frc.robot.Constants.LimelightConstants;

public class Shuffleboard extends SubsystemBase {

    private static Shuffleboard instance;

    private Drive drive;
    private Limelight limelight;    

    private Shuffleboard() {
        drive = Drive.getInstance();
        limelight = Limelight.getInstance();
    }

    public static Shuffleboard getInstance() {
        if (instance == null) {
            instance = new Shuffleboard();
        }

        return instance;
    }

    public void logLimelight() {
        SmartDashboard.putNumber("Distance", limelight.getDistance(limelight.pipe == 0 ? FieldConstants.APRIL_GRID : FieldConstants.TAPE));
        SmartDashboard.putNumber("Pipe", limelight.pipe);
    }

    @Override
    public void resetSensors() {
        
    } 
}