package frc.robot.subsystems;


import edu.wpi.first.wpilibj.AnalogPotentiometer;


public final class LightSensor extends SubsystemBase {
    
    private static LightSensor instance;
    private static AnalogPotentiometer Sensor;
   
    private LightSensor(int pnumber) {
        Sensor = new AnalogPotentiometer(pnumber); 
        resetSensors();
    }

        public static LightSensor getInstance(int pnumber) {
            if (instance == null) {
                instance = new LightSensor(pnumber);
            }

            return instance;
        }
        public double getLightVoltage() {
            //SmartDashboard.putNumber("Light level (volts)", get());
            System.out.println(100 * Sensor.get());
            return 100 * Sensor.get(); 
            
        }

        @Override
        public void resetSensors() {
        
        }

    
}

