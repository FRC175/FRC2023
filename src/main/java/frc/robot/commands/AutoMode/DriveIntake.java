package frc.robot.commands.AutoMode;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Intake.RunIntake;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public final class DriveIntake extends SequentialCommandGroup {
    public DriveIntake(Intake intake, Drive drive) {
        addCommands(
            new DriveAuto(drive, 45),
            new DeployIntake(intake, true),
            new RunIntake(intake, 0.2),
            new WaitCommand(1),
            new RunIntake(intake, 0)
           
        );
    }

   
}