package frc.robot.utils;

import com.revrobotics.CANSparkMax;

public final class DriveHelper {

    // Spark Maxes
    private final CANSparkMax left, right;

    /**
     * Constructs a new DriveHelper.
     *
     * @param left  The master Spark Max for the left drive motors
     * @param right The master Spark Max for the right drive motors
     */
    public DriveHelper(CANSparkMax left, CANSparkMax right) {
        this.left = left;
        this.right = right;
    }

    // BASIC ARCADE DRIVE (PUT THE MATH IN)
    public void arcadeDrive(double throttle, double turn) {
        // MATH
    }
}
