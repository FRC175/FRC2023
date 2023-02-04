package frc.robot.commands.auto;
import frc.robot.commands.Intake.RunIntake;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public final class IntakePlaceHolder extends SequentialCommandGroup{
    public IntakePlaceHolder(Intake intake) {
        addCommands(
            new RunIntake(intake, true),
            new WaitCommand(1),
            new RunIntake(intake, false)
        );
    }

   
}
