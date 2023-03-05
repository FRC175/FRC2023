package frc.robot.commands.drive;

import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

// ruminating

public class Balancing extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;

    public Balancing(Drive drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {

	}

    @Override
    public void execute() {
        if (drive.getAngle() > 35.0) {
            drive.arcadeDrive(0.2, 0);
        } else if (drive.getAngle() < -35.0) {
            drive.arcadeDrive(-0.2, 0);
        } else {
            drive.arcadeDrive(drive.getAngle() * 0.15 / 35.0, 0);
        }
        SmartDashboard.putBoolean("is Balancing", true);
    }

    @Override
    public void end(boolean interrupted) {
        drive.setOpenLoop(0.0, 0.0);
        SmartDashboard.putBoolean("is Balancing", false);

    } 

    @Override
    public boolean isFinished() {
        return false;
    }
}