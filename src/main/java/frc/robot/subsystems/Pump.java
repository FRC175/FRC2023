package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class Pump {

	public static Pump instance;

	private Compressor phCompressor;

	public static Pump getInstance() {
		if (instance == null) {
			instance = new Pump();
		}

		return instance;
	}

	private Pump() {
		phCompressor = new Compressor(Constants.PH_PORT, PneumaticsModuleType.REVPH);
		phCompressor.enableDigital();
	}

}
