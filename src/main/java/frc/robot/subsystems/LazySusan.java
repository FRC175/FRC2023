
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.Constants.LazySusanConstants;

public abstract class LazySusan implements Subsystem {

    private static LazySusan instance; 

    private TalonSRX lazySusan; 

    private LazySusan() { 
        lazySusan = new TalonSRX(Constants.LazySusanConstants.LAZY_SUSAN_PORT); 

        configureTalons();
        resetSensors();
    } 

    private void configureTalons() {
        lazySusan.configFactoryDefault();
        lazySusan.setInverted(false);
        lazySusan.setNeutralMode(NeutralMode.Brake);
    }

    public static LazySusan getInstance() {
        if (instance == null) {
            instance = new LazySusan() {
            };
        }

        return instance;
    }

    public void susanSpin(double speed) {
        lazySusan.set(ControlMode.PercentOutput, speed);
    }


   

    // RESET ENCODERS
    public void resetSensors() {

    }


}