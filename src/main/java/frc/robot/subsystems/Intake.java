package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.IntakeConstants;

public final class Intake extends SubsystemBase {
    
    private static Intake instance;
    
    private final CANSparkMax pullerMaster, pullerSlave;

    private Intake() {
        
        // INSTANTIATE MOTORS HERE

        pullerMaster = new CANSparkMax(IntakeConstants.PULLER_MASTER, MotorType.kBrushless);
        pullerSlave = new CANSparkMax(IntakeConstants.PULLER_SLAVE, MotorType.kBrushless);



        configureSparks();

        // does nothing
        resetSensors();
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    private void configureSparks() {
        pullerSlave.follow(pullerMaster);
    }

    public void setOpenLoop(double demand) {
        pullerMaster.set(demand);
    }
    // PUT DRIVE METHODS HERE (ARCADE, ACCEL)

    @Override
    public void resetSensors() {
        // OVERRIDE COMMAND FROM BASE (ENCODER RESETING GOES HERE)
    }
}
