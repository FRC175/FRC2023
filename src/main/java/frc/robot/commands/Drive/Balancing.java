package frc.robot.commands.drive;

import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;




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
        drive.resetSensors();
    }

    @Override
    public void execute() {
        if (drive.getAngle() > 35.0) {
            drive.setOpenLoop(-1.0, -1.0);
        } else if (drive.getAngle() < -35.0) {
            drive.setOpenLoop(1.0, 1.0);
        } else {
            drive.setOpenLoop(drive.getAngle() * -1.0 / 35.0, drive.getAngle() * -1.0 / 35.0);
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
        if (colorSensor.determineRB()) {
            drive.setOpenLoop(0.0, 0.0);
            return true;
        }
        return false;
    }
}