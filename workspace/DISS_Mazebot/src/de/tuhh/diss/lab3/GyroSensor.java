package de.tuhh.diss.lab3;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class GyroSensor {
    EV3GyroSensor sensorObject = new EV3GyroSensor(SensorPort.S3);
    public void setSensorObject(EV3GyroSensor sensorObject) {
        this.sensorObject = sensorObject;
        sensorObject.reset();
    }

    /**
     * Method description: Angle and turn measurement using EV3 GyroSensor
     * @return Current Angle (float) and number of turns (float)
     */
    public float[] gyroMeasure() {
        SampleProvider gyroSample = sensorObject.getAngleMode();
        int turns = 0;
        int totalAngle = 0;

        float[] gyroAngle = new float[gyroSample.sampleSize()];

        gyroSample.fetchSample(gyroAngle, 0);
        float angle = gyroAngle[0];
        totalAngle += angle;

        if(totalAngle >= 360) {
            turns = (int)totalAngle/360;
            totalAngle -= (360*turns);
        }

        else if(totalAngle <= -360) {
            turns = (int)totalAngle/360;
            totalAngle -= (360*turns);
        }
        return new float[] {angle,turns};

    }

    public static void main(String[] args) {

        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        GyroSensor gyroObject = new GyroSensor();
        leftMotor.setSpeed(200);
        rightMotor.setSpeed(200);

        rightMotor.forward();
        leftMotor.backward();

        while (true) {
            float[] gyroOut = gyroObject.gyroMeasure();
            LCD.refresh();
            LCD.drawString("Angle: " + gyroOut[0], 0, 0);
            LCD.drawString("Turns: " + Math.abs(gyroOut[1]), 0, 1);
        }
    }
}