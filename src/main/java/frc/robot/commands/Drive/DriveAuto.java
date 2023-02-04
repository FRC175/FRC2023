package frc.robot.commands.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;


public class DriveAuto extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final int counts; 

    public DriveAuto( Drive drive, int counts) {
        this.drive = drive;
        this.counts = counts;
        
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetSensors();
    }

    @Override
    public void execute() {
        drive.arcadeDrive(counts < 0 ? - 0.75 : 0.75, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drive.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (counts > 0) return !(drive.getRightCounts() <= counts);
        else return !(drive.getRightCounts() >= counts);
    }
}
