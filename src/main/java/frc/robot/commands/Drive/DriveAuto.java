package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveAuto extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final int counts; 
    private final double speed;

    public DriveAuto( Drive drive, int counts, double speed) {
        this.drive = drive;
        this.counts = counts;
        this.speed = speed;
        
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetSensors();
    }

    @Override
    public void execute() {
        drive.arcadeDrive(counts < 0 ? -1 * speed : speed, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drive.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (counts > 0) return !(drive.getMasterPositions()[0] <= counts);
        else return !(drive.getMasterPositions()[0] >= counts);
    }
}
