// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	public static int PH_PORT = 18;

	public static final class ControllerConstants {
		public static final int DRIVER_CONTROLLER_PORT = 0;
		public static final int OPERATOR_CONTROLLER_PORT = 1;

		public static final double CONTROLLER_DEADBAND = 0.1;
	}

	public static final class DriveConstants {

		public static final int RIGHT_MASTER_PORT = 2;
		public static final int RIGHT_SLAVE_PORT = 3;
		public static final int LEFT_MASTER_PORT = 4;
		public static final int LEFT_SLAVE_PORT = 5;

		public static final Port GYRO_PORT = SPI.Port.kOnboardCS0;

		public static final double MAX_RPM = 5700.0;
	}

	public static final class ArmConstants {
		public static final int TELESCOPE_FORWARD_CHANNEL = 4;
		public static final int TELESCOPE_REVERSE_CHANNEL = 3;
		public static final int ARM_ROTATER_PORT = 10;
		public static final int BRAKE_CHANNEL = 2;
		public static final int LOW_ENCODER_COUNT = 5;
		public static final int MEDIUM_ENCODER_COUNT = 15;
		public static final int HIGH_ENCODER_COUNT = 25;
		public static final int INSIDE_ENCODER_COUNT = 8;
	}

	public static final class GripperConstants {
		public static final int GRIPPER_FORWARD_CHANNEL = 0;
		public static final int GRIPPER_REVERSE_CHANNEL = 1;
	}

	public static final class LEDConstants {
		public static final int BLINKIN_PORT = 1;
	}

	public static final class LimelightConstants {
		public static final double MOUNT_ANGLE = 8.0;
		public static final double MOUNT_HEIGHT = 32.5;
	}

	public static final class FieldConstants {
		public static final double TAPE = 56.0;
		public static final double APRIL_GRID = 41.5;
		public static final double APRIL_STATION = 59.0;
	}
}
