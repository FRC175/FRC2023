// package frc.robot.commands.auto;
// import frc.robot.commands.Intake.RunIntake;
// import frc.robot.commands.drive.DriveAuto;
// import frc.robot.subsystems.Drive;
// import frc.robot.subsystems.Intake;

// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;


// public final class Emotes extends SequentialCommandGroup{
//     public Emotes(Intake intake, Drive drive) {
//         addCommands(
//             new DriveAuto(drive, -70),
//             new WaitCommand(1),
//             new DriveAuto(drive, 70),
//             new WaitCommand(2),
//             new DriveAuto(drive, 0),
//             new RunIntake(intake, isFinished(true))

            
//         );
//     }

   
// }