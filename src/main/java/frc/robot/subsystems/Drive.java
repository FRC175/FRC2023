package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants.DriveConstants;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.utils.DriveHelper;

public final class Drive extends SubsystemBase {

    // DEFINE MOTORS AND DRIVEHELPER HERE
    
    private static Drive instance;
    
    private Drive() {
        
        // INSTANTIATE MOTORS HERE

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

    private void configureSparks() {
        // RESTORE FACTORY DEFAULTS, SET INVERTED AND FOLLOWERS
    }

    // PUT DRIVE METHODS HERE (ARCADE, ACCEL)

    @Override
    public void resetSensors() {
        // OVERRIDE COMMAND FROM BASE (ENCODER RESETING GOES HERE)
    }
}
