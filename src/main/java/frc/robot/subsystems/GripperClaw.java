package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.Constants.GripperConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class GripperClaw implements Subsystem {
	
	private static GripperClaw instance;
	
	public final TalonSRX gripperMotorLeft, gripperMotorRight;

	private final DoubleSolenoid grip;
	
	private boolean gripGripped;

	private GripperClaw() {
		
		gripperMotorLeft = new TalonSRX(Constants.GripperConstants.GRIPPER_MOTOR_PORT_RIGHT);
		gripperMotorRight = new TalonSRX(0);

		grip = new DoubleSolenoid(Constants.PH_PORT, PneumaticsModuleType.REVPH,
				GripperConstants.GRIPPER_FORWARD_CHANNEL, GripperConstants.GRIPPER_REVERSE_CHANNEL);
		gripGripped = false;
		configureTalons();
		setGripClosed();
	}

	private void configureTalons() {
		gripperMotorLeft.configFactoryDefault();
		gripperMotorLeft.setInverted(false);
		
		gripperMotorRight.configFactoryDefault();
		gripperMotorRight.setInverted(false);
	}

	public static GripperClaw getInstance() {
		if (instance == null) {
			instance = new GripperClaw();
		}

		return instance;
	}
	
	public void setOpenLoop(double gripPower) {
		gripperMotorLeft.set(ControlMode.PercentOutput, gripPower);
		gripperMotorRight.set(ControlMode.PercentOutput, gripPower);
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

	
	// RESET ENCODERS
	public void resetSensors() {

	}
}
