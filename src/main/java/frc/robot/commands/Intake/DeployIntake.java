package frc.robot.commands.Intake;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class DeployIntake extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // CHANGE THIS TO USED SUBSYSTEM
  private final Intake intake;
  private final boolean deploy; 

  public DeployIntake(Intake intake, boolean deploy) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
    this.intake = intake;
    this.deploy = deploy; 
    // ADD SUBSYSTEM TO REQUIREMENTS
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.resetSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { 
    if (deploy) {
        intake.setDeployOpenLoop(0.2); 
    } else {
        intake.setDeployOpenLoop(-0.2);
    } 

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setDeployOpenLoop(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(deploy) {
        if(intake.getDeployCounts() >= 420) return true; 
    } else {
        if(intake.getDeployCounts() <= 0) return true; 
    }
    return false; 
  }
}

