package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;

public class SetArmPositionHigh extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SigularField"})
    private final Arm arm;

    public SetArmPositionHigh(Arm arm) {
        this.arm =arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        arm.setBrake(false);
        //FIX NUMBER LATER
        if (arm.getArmState() == ArmState.HIGH) {
            arm.setOpenLoop(0.0);
        } else {
            arm.setOpenLoop(0.2);
        }
    }

    @Override
    public void end(boolean interrupted) {
        arm.setArmState(ArmState.HIGH);
        arm.setBrake(true);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(arm.getEncoderCount() - ArmConstants.HIGH_ENCODER_COUNT) <= 1.0;
    }

}


