// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.Balancing;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.*;
import frc.robot.commands.Emotes;

public class RobotContainer {
  // THIS IS A PLACEHOLDER
  private final SubsystemBase exampleSubsystem = new SubsystemBase() {
    public void resetSensors() {
    };
  };
  // private final ExampleCommand m_autoCommand = new
  // ExampleCommand(m_exampleSubsystem);
  private final Drive drive;
  private final Intake intake;
  private final ColorSensor colorSensor;
  private final Arm arm;
  private final LED led;
  private final XboxController driverController;
  private final XboxController operatorController;
  private final SendableChooser<Command> autoChooser;
  private final Emotes emote;

  private static RobotContainer instance;

  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance();
    colorSensor = ColorSensor.getInstance();
    arm = Arm.getInstance();
    led = LED.getInstance();
    driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
    operatorController = new XboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);
    emote = new Emotes(drive, arm);

    autoChooser = new SendableChooser<>();

    // Configure the default commands
    configureDefaultCommands();

    // Configure the button bindings
    configureButtonBindings();

    // Configure auto mode
    configureAutoChooser();
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
      instance = new RobotContainer();
    }

    return instance;
  }

  private void configureDefaultCommands() {
    // GOAL: INSTEAD OF RUNCOMMAND() HAVE IT BE A ACTUAL COMMAND TO MAKE THIS ONE
    // LINE ONLY!!!
    // drive.setDefaultCommand(new RunCommand(() -> {System.out.println("Drive
    // Command");}, drive));
    // intake.setDefaultCommand(new ColorDrive(colorSensor, intake));

    // colorSensor.setDefaultCommand(new GetColor(colorSensor));
    // colorSensor.setDefaultCommand(new RunCommand(() -> {
    // SmartDashboard.putNumber("distance", colorSensor.getDistance());
    // }, colorSensor));
  
      colorSensor.setDefaultCommand(new RunCommand(() -> {
        System.out.println(colorSensor.getColor());
        
      }, colorSensor));

      led.setDefaultCommand(new InstantCommand(() -> {
      led.setLED(0.95);
      }, led));


      drive.setDefaultCommand(new RunCommand(() -> {
        if (driverController.getRightBumper()) {
          drive.extendLat(true);
          drive.latDrive(driverController.getRightTriggerAxis() - driverController.getLeftTriggerAxis());
          SmartDashboard.putBoolean("Lat", true);
        } else {
          drive.extendLat(false);
          drive.latDrive(0);
          SmartDashboard.putBoolean("Lat", false);
        }
      }, drive));
  }

  private void configureButtonBindings() {
    // PUT A COMMAND HERE SIMILAR TO DEFAULT COMMANDS DO IT LIKE THIS
    // new Trigger(() -> driverController.getBButtonPressed())
    // .toggleOnTrue(new GetColor(colorSensor));

    new Trigger(() -> driverController.getAButton())
    .toggleOnTrue(new RunCommand(() -> {
      intake.setOpenLoop(0.5);
    }, intake))
    .toggleOnFalse(new RunCommand(() -> {
      intake.setOpenLoop(0.0); 
    }, intake)); 

    new Trigger(() -> driverController.getBButton())
    .toggleOnTrue(new Balancing(colorSensor, drive));
  
    new Trigger(() -> driverController.getPOV() == 0)
    .toggleOnTrue(new RunCommand(() -> {
      arm.extend(true);
    }, arm));

    new Trigger(() -> driverController.getPOV() == 180)
    .toggleOnTrue(new RunCommand(() -> {
      arm.extend(false);
    }, arm));

    new Trigger(() -> driverController.getYButton())
    .whileTrue(new RunCommand(() -> { 
      colorSensor.determineGamePiece();
      SmartDashboard.putBoolean("Y Pressed", true);
     }, colorSensor))
     .whileFalse(new RunCommand(() -> {
      SmartDashboard.putBoolean("Y Pressed", false);
     }, colorSensor));

     new Trigger(() -> driverController.getXButton())
     .whileTrue(new RunCommand(() -> { 
        emote.execute(2);
      }))
      .whileFalse(new RunCommand(() -> {
        emote.stop();
      }));

     new Trigger(() -> operatorController.getPOV() ==270)
     .whileTrue(new RunCommand(() -> {
      led.setLED(0.69);
     }, led));

     new Trigger(() -> operatorController.getPOV() ==90)
     .whileTrue(new RunCommand(() -> {
      led.setLED(0.91);
     }, led));

    // new Trigger(() -> driverController.getRightBumper())
    // .whileTrue(new RunCommand(() -> {
    //   drive.extendLat(true);
    //   drive.latDrive(driverController.getRightTriggerAxis() - driverController.getLeftTriggerAxis());
    // }, drive).andThen(() -> {
    //   drive.extendLat(false);
    //   drive.latDrive(0);
    // }, drive));


    //  new Trigger(() -> operatorController.getPOV() ==180)
    //  .whileTrue(new RunCommand(() -> {
    //   led.setLED(0.35);
    //  }, led));
  }


  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Nothing", new WaitCommand(0));
    ;
    // Put auto modes here when ready!
  }

  public Command getAutonomousCommand() {
    // Pulls mode from shuffleboard (do not touch until ready)
    return autoChooser.getSelected();
  }
}



