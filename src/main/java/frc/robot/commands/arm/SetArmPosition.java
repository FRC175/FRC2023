package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;

public class SetArmPosition extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Arm arm;
    private ArmState state;

    public SetArmPosition(Arm arm, ArmState state) {
        this.arm = arm;
        this.state = state;
        addRequirements(arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        arm.setBrakeOff();

        if (arm.getEncoderCount() < state.value)
            arm.setOpenLoop(0.3);
        else if (arm.getEncoderCount() > state.value)
            arm.setOpenLoop(-0.3);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.setArmState(ArmState.MIDDLE);
        arm.setBrakeOn();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(arm.getEncoderCount() - state.value) <= 0.1; // experiment with deadband
    }
}
