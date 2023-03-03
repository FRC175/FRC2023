package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class RunArm extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Arm arm;
  private final XboxController controller;

  public RunArm(Arm arm, XboxController controller) {
    this.arm = arm;
    this.controller = controller;
    addRequirements(arm);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // if L and R pulled
    arm.setBrakeOff();
    arm.setOpenLoop(Math.abs(controller.getRightY()) < 0.1 ? 0 : controller.getRightY());
  }

  @Override
  public void end(boolean interrupted) {
    arm.setOpenLoop(0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(controller.getRightY()) <= 0.10;
  }
}
