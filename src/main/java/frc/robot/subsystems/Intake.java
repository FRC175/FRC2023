package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX; 


// import com.revrobotics.RelativeEncoder;

public class Intake extends SubsystemBase {
   
    private CANSparkMax intakeMaster, intakeSlave;
    private static Intake instance;
    private TalonSRX deployMaster, deploySlave; 
    
    private Intake() {
        intakeMaster = new CANSparkMax(Constants.IntakeConstants.INTAKE_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSlave = new CANSparkMax(IntakeConstants.INTAKE_SLAVE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        deployMaster = new TalonSRX(IntakeConstants.DEPLOY_MASTER_PORT);
        deploySlave = new TalonSRX(IntakeConstants.DEPLOY_SLAVE_PORT);  

        configureMotors();
        resetSensors();
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;

    }

    private void configureMotors() {
        intakeMaster.restoreFactoryDefaults();
        intakeMaster.setInverted(false);

        intakeSlave.restoreFactoryDefaults();
        intakeSlave.setInverted(false);
        intakeSlave.follow(intakeMaster);

        deployMaster.configFactoryDefault();
        deployMaster.setInverted(false);

        deploySlave.configFactoryDefault();
        deploySlave.setInverted(false);
        deploySlave.follow(deployMaster); 
    }
   
    public void setRunOpenLoop(double demand) {
        intakeMaster.set(demand);
    }

    public void setDeployOpenLoop(double demand){ 
        deployMaster.set(ControlMode.PercentOutput, demand); 
    }

    public double getDeployCounts() {
        return deployMaster.getSensorCollection().getQuadraturePosition();
    } 

    @Override
    public void resetSensors() {
        // TODO reset encoder?
    }
}
