package de.tuhh.diss.lab3;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;

public class Task1 {
    EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    /*
     * Method description: Matches the color id and prints color name
     * local variable(s): samples (Array with color id)
     */
    public void getcolorName(){

        SensorMode colorId = colorSensor.getColorIDMode();
        float[] samples = new float[colorId.sampleSize()];
        colorId.fetchSample(samples, 0);

        switch((int)samples[0]) {
        case Color.RED:
            //        	LCD.refresh();
            LCD.drawString("Red", 0, 5);
            break;

        case Color.GREEN:
            LCD.drawString("Green", 0, 5);
            break;

        case Color.BLUE:
            LCD.drawString("Blue", 0, 5);
            break;

        case Color.YELLOW:
            LCD.drawString("yellow", 0, 5);
            break;

        case Color.MAGENTA:
            LCD.drawString("Magneta", 0, 5);
            break;

        case Color.ORANGE:
            LCD.drawString("Orange", 0, 5);
            break;

        case Color.WHITE:
            LCD.drawString("White", 0, 5);
            break;

        case Color.BLACK:
            LCD.drawString("Black", 0, 5);
            break;

        case Color.PINK:
            LCD.drawString("Pink", 0, 5);
            break;

        case Color.GRAY:
            LCD.drawString("Gray", 0, 5);
            break;

        case Color.LIGHT_GRAY:
            LCD.drawString("Light Gray", 0, 5);
            break;

        case Color.DARK_GRAY:
            LCD.drawString("Dark Gray", 0, 5);
            break;

        case Color.BROWN:
            LCD.drawString("Brown", 0, 5);
            break;

        case Color.CYAN:
            LCD.drawString("Cyan", 0, 5);
            break;

        case Color.NONE:
            LCD.drawString("None", 0, 5);
            break;
        default:
            LCD.drawString("Not available", 0, 5);
        }
    }

    public static void main(String[] args) {
        Task1 sensorObject = new Task1();
        while(true) {
            LCD.refresh();
            sensorObject.getcolorName();
        }
    }
}