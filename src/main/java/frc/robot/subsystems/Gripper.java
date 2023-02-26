package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.Constants.GripperConstants;

public class Gripper extends SubsystemBase {

	private static Gripper instance;

	private final DoubleSolenoid grip;
	private boolean gripGripped;

	private Gripper() {
		grip = new DoubleSolenoid(Constants.PH_PORT, PneumaticsModuleType.REVPH,
				GripperConstants.GRIPPER_FORWARD_CHANNEL, GripperConstants.GRIPPER_REVERSE_CHANNEL);
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
		grip.set(gripGripped ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}

	public boolean getGripShiftState() {
		return gripGripped;
	}

	@Override
	public void resetSensors() {

	}
}
