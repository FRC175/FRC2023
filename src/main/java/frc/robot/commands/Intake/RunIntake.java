package frc.robot.commands.Intake;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class RunIntake extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SigngularField"})
    private final Intake intake;
    private boolean runIntake;
  /**
   * Creates a new RunIntake command.
   *
   * @param subsystem The subsystem used by this command.
   */
    public RunIntake (Intake intake, boolean runIntake) {
        this.intake = intake;
        this.runIntake = runIntake;

        addRequirements(intake);

    }
      // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

		intake.setIntakeOpenLoop(runIntake ? -0.65 : 0);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}

