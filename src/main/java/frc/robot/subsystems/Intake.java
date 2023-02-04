package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
// import com.revrobotics.RelativeEncoder;

public class Intake extends SubsystemBase {
   
    private CANSparkMax intakeMaster, intakeSlave;

   
    private static Intake instance;

    private Intake() {
        intakeMaster = new CANSparkMax(Constants.IntakeConstants.INTAKE_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSlave = new CANSparkMax(IntakeConstants.INTAKE_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
            
        configureSparks();

        resetSensors();
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;

    }

    private void configureSparks() {
        intakeMaster.restoreFactoryDefaults();
        intakeMaster.setInverted(false);

        intakeSlave.restoreFactoryDefaults();
        intakeSlave.setInverted(false);
        intakeSlave.follow(intakeMaster);
    }
   
    public void setOpenLoop(double demand) {
        intakeMaster.set(demand);
    }

    @Override
    public void resetSensors() {
        
    }
}
