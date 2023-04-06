package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase {

	private static Arm instance;

	private final CANSparkMax armRotater;
	private final Solenoid brake;
	private boolean brakeSet;

	public static int I = 5;

	public enum ArmState {
		ASLEEP(ArmConstants.ASLEEP_ENCODER_COUNT),
		AWAKE(ArmConstants.AWAKE_ENCODER_COUNT),
		CUBE(ArmConstants.CUBE_ENCODER_COUNT),
		MIDDLE(ArmConstants.MEDIUM_ENCODER_COUNT),
		HIGH(ArmConstants.HIGH_ENCODER_COUNT),
		PORTAL(ArmConstants.PORTAL_ENCODER_COUNT),
		INITIAL(6);

		public double value;

		private ArmState(double value) {
			this.value = value;
		}
	}

	private ArmState state;

	private Arm() {
		armRotater = new CANSparkMax(ArmConstants.ARM_ROTATER_PORT, CANSparkMax.MotorType.kBrushless);
		brake = new Solenoid(Constants.PH_PORT, PneumaticsModuleType.REVPH, ArmConstants.BRAKE_CHANNEL);

		brakeSet = false;
		state = ArmState.INITIAL;

		setOpenLoop(0);

		configureSparks();

		resetSensors();
	}

	public static Arm getInstance() {
		if (instance == null) {
			instance = new Arm();
		}

		return instance;
	}

	private void configureSparks() {
		armRotater.restoreFactoryDefaults();
		armRotater.setInverted(false);
	}

	public void setOpenLoop(double demand) {
		if (!isSafe()) {
			Telescope.getInstance().setExtendOff();
		}
		armRotater.set(-demand);
	}

	public void setBrakeOn() {
		brakeSet = true;
		brake.set(false);
	}

	public void setBrakeOff() {
		brakeSet = false;
		brake.set(true);
	}

	public boolean getBrakeState() {
		return brakeSet;
	}

	public ArmState getArmState() {
		return state;
	}

	public void setArmState(ArmState newState) {
		state = newState;
	}

	public double getEncoderCount() {
		return armRotater.getEncoder().getPosition();
	}

	public boolean isSafe() {
		return getEncoderCount() > 13.0;
	}

	@Override
	public void resetSensors() {
		armRotater.getEncoder().setPosition(0);
	}
}
