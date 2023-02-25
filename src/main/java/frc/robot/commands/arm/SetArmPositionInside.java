package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;

public class SetArmPositionInside extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Arm arm;
  // TO-DO: AHHHHHHHHHHHHHHHHHHHHHHH

  public SetArmPositionInside(Arm arm) {
    this.arm = arm;
    addRequirements(arm);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    arm.setBrake(false);
    if (arm.getArmState() != ArmState.LOW) {
      arm.setOpenLoop(-0.2);
    }
  }

  @Override
  public void end(boolean interrupted) {
    arm.setArmState(ArmState.LOW);
    arm.setBrake(true); 
  }

  @Override
  public boolean isFinished() {
    return arm.getEncoderCount() <= ArmConstants.LOW_ENCODER_COUNT;
  }
}
