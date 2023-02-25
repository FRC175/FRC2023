package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.LimelightConstants;

public class Limelight extends SubsystemBase {

    private static Limelight instance;

    private final NetworkTable table;

    public int pipe = 0;
    
    private Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        table.getEntry("pipeline").setNumber(pipe);
    }

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }
        
        return instance;
    }

    public int switchPipes() {
        if (pipe == 0) pipe = 1;
        else if (pipe == 1) pipe = 0;
        table.getEntry("pipeline").setNumber(pipe);
        return pipe;
    }

    public double getVerticalAngle() {
        return table.getEntry("ty").getDouble(0);
    }

    public double getHorizontalAngle() {
        return table.getEntry("tx").getDouble(0);
    }

    public double getDistance(double h) {
        double x = ((h - LimelightConstants.MOUNT_HEIGHT) / Math.tan(Math.toRadians(LimelightConstants.MOUNT_ANGLE + getVerticalAngle()))); 
        return (x * Math.cos(Math.toRadians(getHorizontalAngle())));
    }

    @Override
    public void resetSensors() {
        
    }
}
