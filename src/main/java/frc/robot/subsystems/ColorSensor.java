// package frc.robot.subsystems;

// import com.revrobotics.ColorMatch;
// import com.revrobotics.ColorSensorV3;
// import edu.wpi.first.wpilibj.I2C;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.util.Color;

// import com.revrobotics.ColorMatchResult;

// public class ColorSensor extends SubsystemBase{
//     //PRIVATE IS ONE OF THREE ACCESS MODIFIERS, FINAL MEANS IT CAN'T CHANGE FROM ITS INITIAL INSTANCE, AND COLORSENSORV3 IS ITS TYPE
//     private final ColorSensorV3 colorSensor;
//     private final ColorMatch colorMatch;
//     private final Color red = new Color(0.5, 0.2, 0.1);
//     private final Color blue = new Color(0.139, 0.429, 0.377);

//     private static ColorSensor instance;

//     //THIS IS A CONSTRUCTOR
//     public ColorSensor() {
//         colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
//         colorMatch = new ColorMatch();

//         configureColorMatches();
//     }

//     //THE RETURN TYPE OF THIS FUNCTION IS COLORSENSOR (NAME OF CLASS BEFORE NAME IS RETURN TYPE)
//     public static ColorSensor getInstance() {
//         if (instance == null) {
//             instance = new ColorSensor();
//         }

//         return instance;
//     }

//     //VOID IS A RETURN TYPE WHICH MEANS ITS NOT RETURNING ANYTHING
//     private void configureColorMatches() {
//         colorMatch.addColorMatch(blue);
//         colorMatch.addColorMatch(red);
//     }

//     public void getColor() {
//         System.out.println("Red " + colorSensor.getColor().red);
//         System.out.println("Blue " + colorSensor.getColor().blue);
//     }

//     public boolean determineColor() {
//         ColorMatchResult match = colorMatch.matchClosestColor(colorSensor.getColor());

//         var result = colorSensor.getColor();
//         SmartDashboard.putString("rgbValue", result.red + "," + result.green + "," + result.blue);

//         if (match.confidence > 0.7 && match.color == red && getDistance() > 500) {
//             SmartDashboard.putBoolean("Is red?", true);
//             return true;
//         }
        
//         SmartDashboard.putBoolean("Is red?", false);
//         return false;
//     }

//     public int getDistance() { 
//         return colorSensor.getProximity();
//     }

//     //@OVERRIDE MEANS WE'RE IMPLIMENTING A METHOD FROM A PRENT CLASS
//     @Override
//     public void resetSensors() {
//         // TODO Auto-generated method stub
        
//     }
    
// }