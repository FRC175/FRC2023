package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class RunArm extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Arm arm;
  private final XboxController controller;

  private boolean isLock = false;
  
  private double goal;

  /**
   * <h1>Maintains the arm's entire current state</h1>
   * <h2>The locking function is run off A button</h2>
   * 
   */
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
    if (Math.abs(controller.getRightY()) > 0.1) {
      arm.setBrakeOff();
      isLock = false;
    } else if (isLock) {
      arm.setBrakeOff();
    } else {
      arm.setBrakeOn();
    }
    if (!isLock) {
      if (controller.getLeftStickButton() && Math.abs(controller.getRightY()) > 0.1) {
        arm.setOpenLoop(controller.getRightY() / 6.0);
      } else if (Math.abs(controller.getRightY()) > 0.1) {
        arm.setOpenLoop(controller.getRightY() / 3.0);
      } else {
        arm.setOpenLoop(0);
      }
      
    } else {
      if (controller.getLeftY() > 0.10) {
        goal -= 0.1;
      } else if (controller.getLeftY() < -0.10) {
        goal += 0.1;
      }
      if (goal - arm.getEncoderCount() > 0.040) {
        arm.setOpenLoop(-0.1);
      } else if (goal - arm.getEncoderCount() < -0.040) {
        arm.setOpenLoop(0.1);
      } else {
        arm.setOpenLoop(0);
      }
      SmartDashboard.putNumber("Arm Encoder Goal", goal);
    }
    if (controller.getAButtonReleased()) {
      isLock = !isLock;
      goal = arm.getEncoderCount();
    }
    SmartDashboard.putBoolean("Is Locked?", isLock);
    
  }

  @Override
  public void end(boolean interrupted) {
    arm.setOpenLoop(0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(controller.getRightY()) <= 0.10 && !isLock;
  }
}
