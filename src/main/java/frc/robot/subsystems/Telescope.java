package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Telescope extends SubsystemBase {

	private static Telescope instance;

	private final DoubleSolenoid telescope;
	private boolean telescopeExtended;

	private Telescope() {
		telescope = new DoubleSolenoid(Constants.PH_PORT, PneumaticsModuleType.REVPH,
				ArmConstants.TELESCOPE_FORWARD_CHANNEL, ArmConstants.TELESCOPE_REVERSE_CHANNEL);

		telescopeExtended = false;

		setExtendOff();
		resetSensors();
	}

	public static Telescope getInstance() {
		if (instance == null) {
			instance = new Telescope();
		}

		return instance;
	}

	public void setExtendOn() {
        if(Arm.getInstance().isSafe()) {
            telescopeExtended = true;
            extend(false);
        }
	}

	public void setExtendOff() {
		telescopeExtended = false;
		extend(true);
	}

	public void extend(boolean extend) {
		telescope.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}

	public boolean getTeleShiftState() {
		return telescopeExtended;
	}

	@Override
	public void resetSensors() {
	}
}
