package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase{
  
    private static Arm instance;

    private final DoubleSolenoid solenoidRight;

    private Arm() {
       solenoidRight = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, ArmConstants.SHIFTER_FORWARD_CHANNEL, ArmConstants.SHIFTER_REVERSE_CHANNEL); 
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }

        return instance;
    }

// pete is sometimes wrong 
    public void extend(boolean extend) {
        solenoidRight.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
        SmartDashboard.putBoolean("Is Extend?", extend);
    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    }


}
