package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

public class ColorSensor extends SubsystemBase{
    //PRIVATE IS ONE OF THREE ACCESS MODIFIERS, FINAL MEANS IT CAN'T CHANGE FROM ITS INITIAL INSTANCE, AND COLORSENSORV3 IS ITS TYPE
    private final ColorSensorV3 colorSensor;
    private final ColorMatch colorMatch;
    private final Color red = new Color(0.439, 0.394, 0.165);
    private final Color blue = new Color(0.139, 0.429, 0.377);

    private static ColorSensor instance;

    //THIS IS A CONSTRUCTOR
    public ColorSensor() {
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        colorMatch = new ColorMatch();

        configureColorMatches();
    }

    //THE RETURN TYPE OF THIS FUNCTION IS COLORSENSOR (NAME OF CLASS BEFORE NAME IS RETURN TYPE)
    public static ColorSensor getInstance() {
        if (instance == null) {
            instance = new ColorSensor();
        }

        return instance;
    }

    //VOID IS A RETURN TYPE WHICH MEANS ITS NOT RETURNING ANYTHING
    private void configureColorMatches() {
        colorMatch.addColorMatch(blue);
        colorMatch.addColorMatch(red);
    }

    public void getColor() {
        System.out.println("Red " + colorSensor.getColor().red);
        System.out.println("Blue " + colorSensor.getColor().blue);
    }

    public boolean determineColor() {
        ColorMatchResult match = colorMatch.matchClosestColor(colorSensor.getColor());

        if (match.color == red) {
            return true;
        }
        
        return false;
    }

    //@OVERRIDE MEANS WE'RE IMPLIMENTING A METHOD FROM A PRENT CLASS
    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    }
    
}
