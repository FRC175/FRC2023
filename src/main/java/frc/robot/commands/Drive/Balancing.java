package frc.robot.commands.drive;

import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

// ruminating

public class Balancing extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ColorSensor colorSensor;
    private final Drive drive;

    public Balancing(ColorSensor colorSensor, Drive drive) {
        this.colorSensor = colorSensor;
        this.drive = drive;
        addRequirements(colorSensor, drive);
    }

    @Override
    public void initialize() {

	}

    @Override
    public void execute() {
        System.out.println("going");
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
        // if (Math.abs(drive.getAngle()) < 5.0) {
        //     drive.setOpenLoop(0.0, 0.0);
        //     return true;
        // }
        return false;
    }
}