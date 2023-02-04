package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

public class ColorSensor extends SubsystemBase{

    private static ColorSensor instance;

    private final ColorSensorV3 colorSensor;

    private final ColorMatch colorMatch;
    private final ColorMatch colorMatchGamePiece;

    private final Color red = new Color(0.5, 0.2, 0.1);
    private final Color blue = new Color(0.139, 0.429, 0.377);
    private final Color purple = new Color(0.21, 0.35, 0.46);
    private final Color yellow = new Color(0.36, 0.54, 0.09);

    public ColorSensor() {
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        colorMatch = new ColorMatch();
        colorMatchGamePiece = new ColorMatch();

        configureColorMatches();
    }

    public static ColorSensor getInstance() {
        if (instance == null) {
            instance = new ColorSensor();
        }

        return instance;
    }

    private void configureColorMatches() {
        colorMatch.addColorMatch(blue);
        colorMatch.addColorMatch(red);
        colorMatchGamePiece.addColorMatch(yellow);
        colorMatchGamePiece.addColorMatch(purple);
        colorMatchGamePiece.addColorMatch(blue);
        colorMatchGamePiece.addColorMatch(red);
    }

    public Color getColor() {
        return colorSensor.getColor();
    }

    public boolean determineRB() {
        ColorMatchResult match = colorMatch.matchClosestColor(colorSensor.getColor());

        if (match.confidence > 0.85 && (match.color == red || match.color == blue)) return true;
        else return false;
    }

    public char determineGP() {
        ColorMatchResult match = colorMatchGamePiece.matchClosestColor(colorSensor.getColor());

        if (match.confidence > 0.95 && match.color == yellow) return 'y';
        else if (match.confidence > 0.7 && match.color == purple) return 'p';
        else return 'n';
    }


    public int getDistance() { 
        return colorSensor.getProximity();
    }

    @Override
    public void resetSensors() {
        
    }
}
