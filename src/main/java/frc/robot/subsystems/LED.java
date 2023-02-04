package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

import frc.robot.Constants.LEDConstants;

public final class LED extends SubsystemBase {

    private final Spark blinkin;

    private static LED instance;

    private LED() {
        blinkin = new Spark(LEDConstants.BLINKIN_PORT);
    }

    public void setLED(double value) { 
        blinkin.set(value); 
    }

    public static LED getInstance() {
        if (instance == null) {
            instance = new LED();
        }
        return instance;
    }

    @Override
    public void resetSensors() {
        
    }
}
