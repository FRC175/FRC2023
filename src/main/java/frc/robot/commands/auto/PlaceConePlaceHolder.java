package frc.robot.commands.auto; 

import frc.robot.subsystems.Drive;
import frc.robot.commands.arm.SetArmPositionFloor;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.subsystems.Arm; 

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PlaceConePlaceHolder extends SequentialCommandGroup {
    public PlaceConePlaceHolder(Arm arm, Drive drive) { 
        addCommands(
            new DriveAuto(drive, 90),
            new WaitCommand(0.5),
            new SetArmPositionFloor(arm)
        );
    }
        
    }
