package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.LEDConstants;

public final class LED extends SubsystemBase {

    private static class ColorNode {
        ColorNode nextNode = null;
        ColorNode prevNode = null;
        double value;
        public ColorNode(double value) {
            this.value = value;
        }
        
        void setNext(ColorNode next) {
            this.nextNode = next;
            next.prevNode = this;
        }
    }

    private static class ColorCycle {
        private ColorNode off = new ColorNode(0.95);
        private ColorNode yellow = new ColorNode(0.69);
        private ColorNode purple = new ColorNode(0.91);
        private ColorNode current;

        private ColorCycle() {
            off.setNext(purple);
            purple.setNext(yellow);
            yellow.setNext(off);
            current = off;
        }

        public void cycle(boolean forward) {
            current = forward ? current.nextNode : current.prevNode;
        }

        public double getColorCode() {
            return current.value;
        }
    }

    private static LED instance;

    private final Spark blinkin;
    private ColorCycle colorCycle;

    private LED() {
        blinkin = new Spark(LEDConstants.BLINKIN_PORT);
        colorCycle = new ColorCycle();

        setLED(colorCycle.getColorCode());
        sendColorToShuffleboard();
    }

    public static LED getInstance() {
        if (instance == null) {
            instance = new LED();
        }
        return instance;
    }

    public void cycleColor(boolean forward) {
        colorCycle.cycle(forward);
        setLED(colorCycle.getColorCode());
    }

    public void setLED(double value) { 
        blinkin.set(value); 
    }

    public double getColor() {
        return colorCycle.getColorCode();
    }

    public void sendColorToShuffleboard() {
        SmartDashboard.putData(new Sendable() {

            @Override
            public void initSendable(SendableBuilder builder) {
              builder.addDoubleProperty("ColorWidget", this::get, null);
            }
      
            double get() {
              return getColor();
            }
            
        });
    }

    @Override
    public void resetSensors() {
        
    }
}
