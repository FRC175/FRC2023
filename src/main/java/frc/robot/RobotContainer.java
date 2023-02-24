package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.arm.SetArmPositionHigh;
import frc.robot.commands.arm.SetArmPositionFloor;
import frc.robot.commands.arm.SetArmPositionMiddle;
import frc.robot.commands.auto.*;
import frc.robot.commands.drive.DriveAuto;
import frc.robot.commands.gripper.Grip;
import frc.robot.commands.led.CycleColor;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shuffleboard;

public class RobotContainer {
  private final Drive drive;
  private final Arm arm;
  private final Gripper gripper;
  private final ColorSensor colorSensor;
  private final LED led;
  private final Shuffleboard shuffleboard;
  private final Limelight limelight;

  private final XboxController driverController;
  private final XboxController operatorController;
  private final SendableChooser<Command> autoChooser;

  private static RobotContainer instance;

  public RobotContainer() {
    drive = Drive.getInstance();
    arm = Arm.getInstance();
    gripper = Gripper.getInstance();
    colorSensor = ColorSensor.getInstance();
    led = LED.getInstance();
    shuffleboard = Shuffleboard.getInstance();
    limelight = Limelight.getInstance();

    driverController = new XboxController(ControllerConstants.DRIVER_CONTROLLER_PORT);
    operatorController = new XboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

    autoChooser = new SendableChooser<>();

    CameraServer.startAutomaticCapture();

    gripper.gripped(false);

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
      arm.setBrake(true);
    }, arm));
    
    // Sends Data to Shuffleboard
    shuffleboard.setDefaultCommand(new RunCommand(() -> {
      shuffleboard.logColorSensor();
      shuffleboard.logDrive();
      shuffleboard.logLimelight();
      shuffleboard.logArm();
      shuffleboard.logGripper();
      shuffleboard.logLED();
    }, shuffleboard));
  }

  private void configureButtonBindings() {
    // Operator Y: Extend Arm
    new Trigger(() -> operatorController.getYButton())
    .whileTrue(new RunCommand(() -> {
      arm.extend(true);
    }, arm));

    // Operator X: Unextend Arm
    new Trigger(() -> operatorController.getXButton())
    .whileTrue(new RunCommand(() -> {
      arm.extend(false);
    }, arm));

    // Operator RT: Grip Gripper
    new Trigger(() -> operatorController.getAButton())
    .onTrue(new Grip(gripper, true))
    .onFalse(new Grip(gripper, false));
     
    // Operator D-Pad Down: Arm to Floor setpoint
    new Trigger(() -> operatorController.getPOV() == 180)
    .onTrue(new SetArmPositionFloor(arm));
     
    // Operator D-Pad Right or Left: Arm to Middle setpoint
    new Trigger(() -> driverController.getPOV() == 270 || operatorController.getPOV() == 90)
    .onTrue(new SetArmPositionMiddle(arm));

    // Operator D-Pad Up: Arm to High setpoint
    new Trigger(() -> driverController.getPOV() == 0)
    .onTrue(new SetArmPositionHigh(arm));

    // Operator RB: Cycle LED Right
    new Trigger(() -> operatorController.getRightBumper())
    .onTrue(new CycleColor(led, true));

    // Operator LB: Cycle LED Left
    new Trigger(() -> operatorController.getLeftBumper())
    .onTrue(new CycleColor(led, false));

    // Operator START: Switch Pipes
    new Trigger(() -> operatorController.getStartButton())
    .toggleOnTrue(new InstantCommand(() -> {
      limelight.switchPipes();
    }, limelight));
  }
 

  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Nothing", new WaitCommand(0));
    autoChooser.addOption("Drive Tarmac", new DriveAuto(drive, 70));
    autoChooser.addOption("Auto Balance", new DriveThenBalance(drive, colorSensor));
    autoChooser.addOption("Auto Balance", new DriveThenBalance(drive, colorSensor));

    SmartDashboard.putData(autoChooser);
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
