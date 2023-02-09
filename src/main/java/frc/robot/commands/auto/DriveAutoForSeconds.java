package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveAutoForSeconds extends CommandBase {

    private boolean isStarted = false;
    private double startTime;
    private final Drive drive;
    private final double seconds;
    private final double speed;

    public DriveAutoForSeconds(Drive drive, double seconds, double speed) {
        this.drive = drive;
        this.seconds = seconds;
        this.speed = speed;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetSensors();
    }

     @Override
     public void execute() {
        if (!isStarted) {
            startTime = Timer.getFPGATimestamp();
            isStarted = true;
        }
        drive.arcadeDrive(speed, 0);
     }

     // called once the command ends or is interrupted.
     @Override
     public void end(boolean interrupted) {
         drive.arcadeDrive(0, 0);
     }

     // returns true when the command should end
     @Override
     public boolean isFinished() {
         return Timer.getFPGATimestamp() - startTime >= seconds;
     }

}