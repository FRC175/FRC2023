package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;
import frc.robot.Constants.GripperConstants;

public class Gripper extends SubsystemBase {

    private static Gripper instance;

    private Value gripForward = DoubleSolenoid.Value.kForward;
    private Value gripBack = DoubleSolenoid.Value.kReverse;



    private final DoubleSolenoid grip;
    private boolean gripGripped;

    private Gripper() {
        grip = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, GripperConstants.GRIPPER_FORWARD_CHANNEL, GripperConstants.GRIPPER_REVERSE_CHANNEL);
        gripGripped = false;
        gripped(false);
    }

    public static Gripper getInstance() {
        if (instance == null) {
            instance = new Gripper();
        }

        return instance;
    }


    public void gripped(boolean extend) {
        gripGripped = extend;
        grip.set(gripGripped ? gripForward : gripBack);
    }

    public boolean getGripShiftState() {
        return gripGripped;
    }

    @Override
    public void resetSensors() {

    }
}
