package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase{
  
    private static Arm instance;

    private final DoubleSolenoid telescope;
    private boolean telescopeExtended;

    private Arm() {
        telescope = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, ArmConstants.TELESCOPE_FORWARD_CHANNEL, ArmConstants.TELESCOPE_REVERSE_CHANNEL); 
        telescopeExtended = false;
        extend(false);
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }

        return instance;
    }

    public void extend(boolean extend) {
        telescopeExtended = extend;
        telescope.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public boolean getTeleShiftState() {
        return telescopeExtended;
    }

    @Override
    public void resetSensors() {
        
    }
}
