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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.SetArmPositionHigh;
import frc.robot.commands.SetArmPositionLow;
import frc.robot.commands.SetArmPositionMiddle;
import frc.robot.commands.Drive.DriveAuto;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shuffleboard;

public class RobotContainer {
  private final Drive drive;
  private final Intake intake;
  private final Arm arm;
  private final Gripper gripper;
  private final LED led;
  private final Shuffleboard shuffleboard;
  private final Limelight limelight;

  private final XboxController driverController;
  private final XboxController operatorController;
  private final SendableChooser<Command> autoChooser;

  private static RobotContainer instance;

  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance();
    arm = Arm.getInstance();
    gripper = Gripper.getInstance();
    led = LED.getInstance();
    shuffleboard = Shuffleboard.getInstance();
    limelight = Limelight.getInstance();

    driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
    operatorController = new XboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

    autoChooser = new SendableChooser<>();

    configureDefaultCommands();
    configureButtonBindings();
    configureAutoChooser();
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
        instance = new RobotContainer();
    }

    return instance;
  }

  private void configureDefaultCommands() {
    // Arcade Drive and Lateral Drive
    drive.setDefaultCommand(new RunCommand(() -> {
      double throttle = driverController.getRightTriggerAxis() - driverController.getLeftTriggerAxis();
      double turn = -1 * driverController.getLeftX();
      if (driverController.getRightBumper()) {
        drive.extendLat(true);
        drive.latDrive(throttle);
        drive.arcadeDrive(0, 0);
      } else {
        drive.latDrive(0);
        drive.extendLat(false);
        drive.arcadeDrive(throttle, turn);
      }
    }, drive));

    // Unextends Arm
    arm.setDefaultCommand(new RunCommand(() -> {
      arm.extend(false);
      arm.setBrake(true);
      arm.setOpenLoop(0.0);
    }, arm));

    //Ungrips Gripper
    gripper.setDefaultCommand(new RunCommand(() -> {
      gripper.gripped(false); 
    }, gripper));

    // Stops intake
    intake.setDefaultCommand(new RunCommand(() -> {
      intake.setOpenLoop(0);
    }, intake));

    // Sets LED to gray
    led.setDefaultCommand(new RunCommand(() -> {
      led.setLED(0.95);
    }, led));

    shuffleboard.setDefaultCommand(new RunCommand(() -> {
      shuffleboard.logColorSensor();
      shuffleboard.logDrive();
      shuffleboard.logLimelight();
      shuffleboard.logArm();
      shuffleboard.logGripper();
    }, shuffleboard));
  }

  private void configureButtonBindings() {
    // Operator Y: Extend Arm
    new Trigger(() -> operatorController.getYButton())
    .whileTrue(new RunCommand(() -> {
      arm.extend(true);
    }, arm));

    // Operator X: Grip Gripper
    new Trigger(() -> operatorController.getXButton())
    .whileTrue(new RunCommand(() -> {
    gripper.gripped(true);
    }, gripper));

    // Operator B: Run Intake
    new Trigger(() -> operatorController.getBButton())
    .whileTrue(new RunCommand(() -> {
      intake.setOpenLoop(0.3);
    }, intake));

    // Driver D-Pad Right: Arm to Low setpoint
    new Trigger(() -> driverController.getPOV() == 90)
    .onTrue(new SetArmPositionLow(arm));

    // Driver D-Pad Left: Arm to Low setpoint
    new Trigger(() -> driverController.getPOV() == 270)
    .onTrue(new SetArmPositionMiddle(arm));

    //Driver D-Pad Up: Arm to High setpoint
    new Trigger(() -> driverController.getPOV() == 0)
    .onTrue(new SetArmPositionHigh(arm));


    // Operator Left: Yellow LED
    new Trigger(() -> operatorController.getPOV() == 270)
    .whileTrue(new RunCommand(() -> {
      led.setLED(0.69);
    }, led));

    // Operator Right: Violet LED
    new Trigger(() -> operatorController.getPOV() == 90)
    .whileTrue(new RunCommand(() -> {
      led.setLED(0.91);
    }, led));

    // Operator START: Switch Pipes
    new Trigger(() -> operatorController.getStartButton())
    .toggleOnTrue(new InstantCommand(() -> {
      limelight.switchPipes();
    }, limelight));


  }

  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Nothing", new WaitCommand(0));
    autoChooser.addOption("Drive Tarmac", new DriveAuto(drive, 70));

    SmartDashboard.putData(autoChooser);
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
