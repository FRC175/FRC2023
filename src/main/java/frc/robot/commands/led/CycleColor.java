package frc.robot.commands.led;

import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class CycleColor extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final LED led;
  private boolean forward;

  public CycleColor(LED led, boolean forward) {
    this.led = led;
    this.forward = forward;
    
    addRequirements(led);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    led.cycleColor(forward);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
