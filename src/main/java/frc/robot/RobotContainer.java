// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.ExampleCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ControllerConstants;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
import frc.robot.models.AdvancedXboxController.Button;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.SubsystemBase;

public class RobotContainer {
  // THIS IS A PLACEHOLDER
  private final SubsystemBase exampleSubsystem = new SubsystemBase(){public void resetSensors() {};};
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Drive drive;

  private final AdvancedXboxController driverController;
  private final AdvancedXboxController operatorController;
  private final SendableChooser<Command> autoChooser;

  private static RobotContainer instance;

  public RobotContainer() {
    drive = Drive.getInstance();

    driverController = new AdvancedXboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
    operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

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
    drive.setDefaultCommand(new RunCommand(() -> {System.out.println("Drive Command");}, drive));
  }

  private void configureButtonBindings() {
    // PUT A COMMAND HERE SIMILAR TO DEFAULT COMMANDS DO IT LIKE THIS
    new XboxButton(driverController, Button.A)
      .whenPressed(new ExampleCommand(exampleSubsystem));
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
