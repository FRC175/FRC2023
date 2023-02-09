// package frc.robot.commands.Gyro;

// import frc.robot.subsystems.ColorSensor;
// import frc.robot.subsystems.Drive;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;




// public class Balancing extends CommandBase {
//     @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//     // CHANGE THIS TO USED SUBSYSTEM
//     private final ColorSensor colorSensor;
//     private final Drive drive;

//     public Balancing(ColorSensor colorSensor, Drive drive) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
//         this.colorSensor = colorSensor;
//         this.drive = drive;
//         // ADD SUBSYSTEM TO REQUIREMENTS
//         addRequirements(colorSensor, drive);
//     }

//     // Called when the command is initially scheduled.
//     @Override
//     public void initialize() {
//         drive.resetGyro();
//     }

//     // Called every time the scheduler runs while the command is scheduled.
//     @Override
//     public void execute() {
//         if (drive.getAngle() > 35.0) {
//             drive.setOpenLoop(-1.0, -1.0);
//         } else if (drive.getAngle() < -35.0) {
//             drive.setOpenLoop(1.0, 1.0);
//         } else {
//             drive.setOpenLoop(drive.getAngle() * -1.0 / 35.0, drive.getAngle() * -1.0 / 35.0);
//         }
//         SmartDashboard.putBoolean("is Balancing", true);
//     }

//     // Called once the command ends or is interrupted.
//     @Override
//     public void end(boolean interrupted) {
//         drive.setOpenLoop(0.0, 0.0);
//     } 

//     // Returns true when the command should end.
//     @Override
//     public boolean isFinished() {
//         if (colorSensor.determineColor()) {
//             drive.setOpenLoop(0.0, 0.0);
//             return true;
//         }
//         return false;
//     }
// }
