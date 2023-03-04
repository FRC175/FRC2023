package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;

public class SetArmPosition extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Arm arm;
    private ArmState state;
    private boolean isFinite;
    private boolean hasHit = false;;

    public SetArmPosition(Arm arm, ArmState state, boolean isFinite) {
        this.arm = arm;
        this.state = state;
        this.isFinite = isFinite;
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
        if (isFinite) {
            if (arm.getEncoderCount() < state.value)
                arm.setOpenLoop(-0.2);
            else if (arm.getEncoderCount() > state.value)
                arm.setOpenLoop(0.2);
        } else {
            if (!hasHit) {
                if (arm.getEncoderCount() < state.value - 0.1)
                    arm.setOpenLoop(-0.2);
                else if (arm.getEncoderCount() > state.value + 0.1)
                    arm.setOpenLoop(0.2);
                else hasHit = true;
            } else {
                if (state.value - arm.getEncoderCount() > 0.1) {
                    arm.setOpenLoop(-0.1);
                } else {
                    arm.setOpenLoop(0);
                }
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.setArmState(state);
        arm.setBrakeOn();
        arm.setOpenLoop(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (isFinite) return Math.abs(arm.getEncoderCount() - state.value) <= 0.1; // experiment with deadband
        else return false;
    }
}
