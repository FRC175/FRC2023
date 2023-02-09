// package frc.robot.commands.auto;
// import frc.robot.commands.Intake.RunIntake;
// import frc.robot.commands.drive.DriveAuto;
// import frc.robot.subsystems.Drive;
// import frc.robot.subsystems.Intake;

// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;


// public final class DriveIntakeIntegrated extends SequentialCommandGroup{
//     public DriveIntakeIntegrated(Intake intake, Drive drive) {
//         addCommands(
//             new DriveAuto(drive, 45),
//             new RunIntake(intake, false),
//             new WaitCommand(1),
//             new RunIntake(intake, true),
//            new WaitCommand(1),
//            new RunIntake(intake, false)
//         );
//     }

   
// }
