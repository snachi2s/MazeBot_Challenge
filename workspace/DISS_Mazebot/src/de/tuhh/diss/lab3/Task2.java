package de.tuhh.diss.lab3;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Task2 {
    //OFFSET --> distance between robot wheel axis and sensor
    final double OFFSET = 4.5; //in cms

    EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);
    SampleProvider distanceSample = distanceSensor.getDistanceMode();
    /*
     * method description: distance measurement between the robot wheel axis and the obstacle
     * local variables: dsArray (Array containing sensor values)
     * returns: (double) distance in cms
     */
    public double distanceMeasure() {
        float[] dsArray = new float[distanceSample.sampleSize()];
        distanceSample.fetchSample(dsArray, 0);
        double distance = (dsArray[0] * 100); // in cms
        return distance + OFFSET;
    }
    public static void main(String[] args) {
        try {
            Task2 usSensor = new Task2();
            while(true) {
                double distance = usSensor.distanceMeasure();
                LCD.refresh();
                LCD.drawString("Distance: " + Double.toString(distance), 0, 0);
            }
        }
        catch (Exception e) {
            LCD.drawString(" "+ e.getLocalizedMessage(),0,0);
        }
    }
}