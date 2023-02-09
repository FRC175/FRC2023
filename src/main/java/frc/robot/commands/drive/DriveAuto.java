// package frc.robot.commands.drive;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Drive;


// public class DriveAuto extends CommandBase {
//     @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//     private final Drive drive;
//     private final int counts; 

//    /**
//     * Creates a new ExampleCommand.
//     *
//     * @param subsystem The subsystem used by this command.
//     */

//     public DriveAuto( Drive drive, int counts) {
//         this.drive = drive;
//         this.counts = counts;
//         // Use addRequirements() here to declare subsystem dependencies.
//         addRequirements(drive);
//     }


//     // called when the command is initially scheduled.
//     @Override
//     public void initialize() {
//         drive.resetSensors();
//     }

//     // Called every time the scheduler runs while the command is scheduled.
//     @Override
//     public void execute() {
//         drive.arcadeDrive( counts < 0 ? - 0.75 : 0.75, 0);
//     }

//     // called once the command ends or is interrupted.
//     @Override
//     public void end(boolean interrupted) {
//         drive.arcadeDrive(0, 0);
//     }

//     // returns true when the command should end
//     @Override
//     public boolean isFinished() {
//         if (counts > 0) return !(drive.rightCounts() <= counts);
//         else return !(drive.rightCounts() >= counts);
//     }
// }
