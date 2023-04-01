package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;

public class IRSensor extends SubsystemBase {
    
    private static IRSensor instance;

    public static AnalogInput irSensor;

    public IRSensor() {
        irSensor = new AnalogInput(0);
    }

    public static IRSensor getInstance() {
		if (instance == null) {
			instance = new IRSensor();
		}

		return instance;
	}

    public double getIRValue() {
        double irVoltage = irSensor.getVoltage();
        // double irDistance = 12.08 * Math.pow(irVoltage, -0.935);
        return irVoltage * 1000;
    }

    public boolean irDetected() {
        return getIRValue() < 300;
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    }

}
