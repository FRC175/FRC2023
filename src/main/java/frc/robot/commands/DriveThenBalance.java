package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;

public class DriveThenBalance extends SequentialCommandGroup {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
    public DriveThenBalance(Drive drive, ColorSensor colorSensor) { // MAKE SURE TO CHANGE DATA TYPE TO SUBSYSTEM
      addCommands(
        new DriveAuto(drive, 70),
        new Balancing(colorSensor, drive)
      );
    }
}
