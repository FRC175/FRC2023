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
		setGripClosed();
	}

	public static Gripper getInstance() {
		if (instance == null) {
			instance = new Gripper();
		}

		return instance;
	}

	public void gripped(boolean extend) {
		grip.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}

	public void setGripOpen() {
		gripGripped = true;
		gripped(true);
	}

	public void setGripClosed() {
		gripGripped = false;
		gripped(false);
	}

	public boolean getGripShiftState() {
		return gripGripped;
	}

	@Override
	public void resetSensors() {

	}
}
