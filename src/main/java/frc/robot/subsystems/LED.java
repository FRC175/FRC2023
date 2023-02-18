package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

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

    private final Spark blinkin;
    private static LED instance;
    private ColorCycle colorCycle = new ColorCycle();

    private LED() {
        blinkin = new Spark(LEDConstants.BLINKIN_PORT);
        setLED(colorCycle.getColorCode());
    }

    public void setLED(double value) { 
        blinkin.set(value); 
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

    @Override
    public void resetSensors() {
        
    }

    public double getColor() {
        return colorCycle.getColorCode();
    }
}
