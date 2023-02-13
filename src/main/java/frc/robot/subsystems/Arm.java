package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase{
  
    private static Arm instance;

    private final DoubleSolenoid telescope;
    private final CANSparkMax armRotater;
    private boolean telescopeExtended;
    private final Solenoid brake; 
    private boolean brakeSet;
    public enum ArmState {
        LOW,
        MIDDLE,
        HIGH
    }
    private ArmState state;

    private Arm() {
        telescope = new DoubleSolenoid(Constants.PCM_PORT, PneumaticsModuleType.CTREPCM, ArmConstants.TELESCOPE_FORWARD_CHANNEL, ArmConstants.TELESCOPE_REVERSE_CHANNEL); 
        armRotater = new CANSparkMax(ArmConstants.ARM_ROTATER_PORT, CANSparkMax.MotorType.kBrushless);
        brake = new Solenoid(PneumaticsModuleType.CTREPCM, ArmConstants.BRAKE_CHANNEL);
        telescopeExtended = false;
        brakeSet = false;
        state = ArmState.LOW;
        extend(false);
     
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
        armRotater.set(demand);
    }

    public void setBrake(boolean braking) {
        brakeSet = braking;
        brake.set(braking);
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

    }
}
