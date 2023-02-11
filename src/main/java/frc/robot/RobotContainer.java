// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.auto.DriveTarmac;
import frc.robot.commands.auto.DriveToDist;
// import frc.robot.commands.auto.DriveIntakeIntegrated;
// import frc.robot.commands.auto.DriveTarmac;
// import frc.robot.commands.auto.IntakePlaceHolder;
// import frc.robot.commands.auto.GyroBalancing;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shuffleboard;
// import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.LightSensor;
// import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.SubsystemBase;

public class RobotContainer {
  // THIS IS A PLACEHOLDER
  private final SubsystemBase exampleSubsystem = new SubsystemBase(){public void resetSensors() {};};
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Drive drive; 
  private final Shuffleboard shuffleboard;
  private final Limelight limelight;

  private final XboxController driverController;
  // private final AdvancedXboxController operatorController;
  private final SendableChooser<Command> autoChooser;
  // private final LightSensor lightSensor;
  // private final Intake intake;
  // private final ColorSensor colorSensor;
  private static RobotContainer instance;


  public RobotContainer() {
    drive = Drive.getInstance();
    shuffleboard = Shuffleboard.getInstance();
    limelight = Limelight.getInstance();
    // lightSensor = LightSensor.getInstance(0);
    driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
    // intake = Intake.getInstance();
    // colorSensor = ColorSensor.getInstance();
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

    shuffleboard.setDefaultCommand(new InstantCommand(() -> {
      shuffleboard.logLimelight();
    }, shuffleboard));


  }

  private void configureButtonBindings() {
    // PUT A COMMAND HERE SIMILAR TO DEFAULT COMMANDS DO IT LIKE THIS
    new Trigger(() -> driverController.getAButton())
      .toggleOnTrue(new InstantCommand(() -> {
        limelight.switchPipes();
      }, limelight));

    new Trigger(() -> driverController.getBButton())
    .whileTrue(new DriveToDist(drive, limelight));
    
      // new Trigger(() -> driverController.getBButton())
      // .toggleOnTrue(new RunCommand(() -> {
      //   lightSensor.getLightVoltage();
      // }, lightSensor));
  }


  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Default", new DriveTarmac(drive));
    // autoChooser.addOption("DriveTarmac", new DriveTarmac(drive));
    // autoChooser.addOption("RunIntake", new IntakePlaceHolder(intake));
    // autoChooser.addOption("DriveThenIntake", new DriveIntakeIntegrated(intake, drive));
    // autoChooser.addOption("Balancing", new GyroBalancing(colorSensor, drive));
    // Put auto modes here when ready!
    
    SmartDashboard.putData(autoChooser);
  }

  public Command getAutonomousCommand() {
    // Pulls mode from shuffleboard (do not touch until ready)
    
    return autoChooser.getSelected();
  }
}