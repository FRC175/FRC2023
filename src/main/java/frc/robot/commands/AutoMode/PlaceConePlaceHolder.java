package frc.robot.commands.AutoMode; 

import frc.robot.subsystems.Drive; 
import frc.robot.subsystems.Intake;
import frc.robot.commands.SetArmPositionLow;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.Arm; 

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceConePlaceHolder extends SequentialCommandGroup {
    public PlaceConePlaceHolder(Arm arm, Drive drive, Intake intake) { 
        addCommands(
            new DriveAuto(drive, 90),
            new WaitCommand(0.5),
            new SetArmPositionLow(arm)
        );
    }
        
    }
