package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase {

	private static Arm instance;

	private final DoubleSolenoid telescope;
	private final CANSparkMax armRotater;
	private boolean telescopeExtended;
	private final Solenoid brake;
	private boolean brakeSet;

	public enum ArmState {
		LOW(ArmConstants.LOW_ENCODER_COUNT),
		MIDDLE(ArmConstants.MEDIUM_ENCODER_COUNT),
		HIGH(ArmConstants.HIGH_ENCODER_COUNT),
		INSIDE(ArmConstants.INSIDE_ENCODER_COUNT);

		public double value;

		private ArmState(double value) {
			this.value = value;
		}
	}

	private ArmState state;

	private Arm() {
		telescope = new DoubleSolenoid(Constants.PH_PORT, PneumaticsModuleType.REVPH,
				ArmConstants.TELESCOPE_FORWARD_CHANNEL, ArmConstants.TELESCOPE_REVERSE_CHANNEL);
		armRotater = new CANSparkMax(ArmConstants.ARM_ROTATER_PORT, CANSparkMax.MotorType.kBrushless);
		brake = new Solenoid(Constants.PH_PORT, PneumaticsModuleType.REVPH, ArmConstants.BRAKE_CHANNEL);

		telescopeExtended = false;
		brakeSet = false;
		state = ArmState.LOW;

		setExtendOff();
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
		if (getEncoderCount() <= 10.0) {
			setExtendOff();
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

	public void setExtendOn() {
		if (getEncoderCount() >= 10.0) {
			telescopeExtended = true;
			extend(false);
		}
	}

	public void setExtendOff() {
		telescopeExtended = false;
		extend(true);
	}

	public void extend(boolean extend) {
		telescopeExtended = extend;
		telescope.set(extend ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}

	public boolean getBrakeState() {
		return brakeSet;
	}

	public boolean getTeleShiftState() {
		return telescopeExtended;
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

	@Override
	public void resetSensors() {
		armRotater.getEncoder().setPosition(0);
	}
}
