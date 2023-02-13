package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;

public class SetArmPositionLow extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Arm arm;


  public SetArmPositionLow(Arm arm) {
    this.arm = arm;
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    arm.setBrake(false);
    //FIX NUMBER LATER
    if (arm.getArmState() != ArmState.LOW) {
      arm.setOpenLoop(-0.2);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.setArmState(ArmState.LOW);
    arm.setBrake(true); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return arm.getEncoderCount() <= ArmConstants.LOW_ENCODER_COUNT;
  }
}
