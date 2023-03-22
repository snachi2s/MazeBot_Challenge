package de.tuhh.diss.lab3;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Task3 {
    public static void main(String[] args) {
        final double THRESHOLD = 6;
        final int MOTORSPEED = 200;

        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);

        Task1 colorObject = new Task1();
        Task2 usObject = new Task2();
        try {
            while(true) {
                //returns current distance from robot to the obstacle (in cms)
                double distance = usObject.distanceMeasure();
                LCD.drawString("Distance: " + String.format("%.2f", distance), 0, 0);
                leftMotor.setSpeed(MOTORSPEED);
                rightMotor.setSpeed(MOTORSPEED);

                leftMotor.backward();
                rightMotor.backward();

                if(distance <= THRESHOLD) {
                    leftMotor.stop(true);
                    rightMotor.stop(true);
                    //returns color name as string 
                    colorObject.getcolorName();
                    break;
                }
            }

        }
        catch(Exception e) {
            LCD.drawString(e.getLocalizedMessage(), 0, 3);
        }
    }
}