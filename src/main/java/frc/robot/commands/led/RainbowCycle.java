package frc.robot.commands.led;

import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RainbowCycle extends CommandBase {
	@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

	private final LED led;
	private int times;
    private int done;


	public RainbowCycle(LED led, int times) {
		this.led = led;
        this.times = times;
        this.done = 0;

		addRequirements(led);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
	    led.rainbow();
        done++;
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return times < done;
	}
}
