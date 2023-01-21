// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class ControllerConstants {
        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double CONTROLLER_DEADBAND = 0.1;
    }

    public static final class DriveConstants {
        // PUT DRIVE MOTOR CONSTANTS HERE
        public static final int RIGHT_MASTER_PORT = 5;
        public static final int RIGHT_SLAVE_PORT = 4;
        public static final int LEFT_MASTER_PORT = 3;
        public static final int LEFT_SLAVE_PORT = 2;
        public static final Port GYRO_PORT = SPI.Port.kOnboardCS0;
    }

    // ADD MORE INNER CLASSES WHEN NEW SUBSYSTEM IS MADE

}
