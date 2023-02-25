package frc.robot.commands.auto; 

import frc.robot.subsystems.Gripper;
import frc.robot.commands.arm.SetArmPositionHigh;
import frc.robot.commands.gripper.Grip;
import frc.robot.subsystems.Arm; 
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceConePlaceHolder extends SequentialCommandGroup {
    // TO-DO: AHHHHHHHHHHHHHHHHHHH
    public PlaceConePlaceHolder(Arm arm, Gripper gripper) { 
        addCommands(
            new SetArmPositionHigh(arm),
            new Grip(gripper, true)
        );
    }
        
    }
