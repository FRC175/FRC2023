// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightSensor;
import frc.robot.subsystems.SubsystemBase;

public class RobotContainer {
  // THIS IS A PLACEHOLDER
  private final SubsystemBase exampleSubsystem = new SubsystemBase(){public void resetSensors() {};};
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Drive drive; 

  private final XboxController driverController;
  // private final AdvancedXboxController operatorController;
  private final SendableChooser<Command> autoChooser;
  private final LightSensor lightSensor;

  private static RobotContainer instance;


  public RobotContainer() {
    drive = Drive.getInstance();
    lightSensor = LightSensor.getInstance(0);
    driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
    // operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

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
    //GOAL: INSTEAD OF RUNCOMMAND() HAVE IT BE A ACTUAL COMMAND TO MAKE THIS ONE LINE ONLY!!!
    // drive.setDefaultCommand(new RunCommand(() -> {System.out.println("Drive Command");}, drive));
    drive.setDefaultCommand(new RunCommand(() -> {
      double throttle = driverController.getRightTriggerAxis() - driverController.getLeftTriggerAxis();
      double turn = -1 * driverController.getLeftX(); 
    //drive.setOpenLoop(0, driverController.getRightTriggerAxis());
    // drive.arcadeDrive(throttle, turn);
      drive.accelDrive(throttle, turn);
    }, drive));


  }

  private void configureButtonBindings() {
    // PUT A COMMAND HERE SIMILAR TO DEFAULT COMMANDS DO IT LIKE THIS
    new Trigger(() -> driverController.getAButton())
      .toggleOnTrue(new RunCommand(() -> {
        System.out.println("toggle on true");
      }, exampleSubsystem));
      new Trigger(() -> driverController.getBButton())
      .toggleOnTrue(new RunCommand(() -> {
        lightSensor.getLightVoltage();
      }, lightSensor));
  }

  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Nothing", new WaitCommand(0));;
    // Put auto modes here when ready!
  }

  public Command getAutonomousCommand() {
    // Pulls mode from shuffleboard (do not touch until ready)
    return autoChooser.getSelected();
  }
}
