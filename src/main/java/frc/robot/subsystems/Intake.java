// package frc.robot.subsystems;

// // import edu.wpi.first.wpilibj.DoubleSolenoid;
// // import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import frc.robot.Constants;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel;
// // import com.revrobotics.RelativeEncoder;

// public class Intake extends SubsystemBase {
   
//     private CANSparkMax intakeMotor;

   
//     private static Intake instance;

//     private Intake() {
//         intakeMotor = new CANSparkMax(Constants.IntakeConstants.INTAKE_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
            
//         configureSparks();

//         resetSensors();
//         }   
//     private void configureSparks() {
      
//         intakeMotor.restoreFactoryDefaults();
//         intakeMotor.setInverted(false);

//     }
   
//     public void setIntakeOpenLoop(double demand) {
//         intakeMotor.set(demand);
//       }
  
//     @Override
//     public void resetSensors() {
//         // TODO Auto-generated method stub
        
//     }
//     public static Intake getInstance() {
//         if (instance == null) {
//             instance = new Intake();
//         }

//         return instance;

//     }
// }
