package frc.robot.commands;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Emotes extends CommandBase {
    private final Drive drive;
    private final Arm arm;
    private boolean armstate = false;
    private int count = 0;

    public Emotes(Drive drive, Arm arm) {
        this.drive = drive; 
        this.arm = arm;
    }

    public void execute(int i) {
        switch (i) {
            case 1:
                drive.setOpenLoop(0.5, -0.5);      
                break;
            case 2:
                arm.extend(armstate);
                if ( count%10 == 0 ) armstate = !armstate;
                break;
        
            default:
                break;
        }
        count ++;  
    }

    public void stop() {
        //use this to turn off all imported subsystems 
        drive.setOpenLoop(0, 0);

    }
}