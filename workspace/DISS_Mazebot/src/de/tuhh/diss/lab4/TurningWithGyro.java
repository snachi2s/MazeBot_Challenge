package de.tuhh.diss.lab4;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class TurningWithGyro implements Turner{
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    EV3GyroSensor sensorObject = new EV3GyroSensor(SensorPort.S3);

    private int degreesPerSecond;

    @Override
    public void setSpeed(int degreesPerSecond) {
        if(degreesPerSecond < 0) {
            this.degreesPerSecond = -degreesPerSecond;
        }
        else {
            this.degreesPerSecond = degreesPerSecond;
        }
    }

    @Override
    public void turn(int degrees) {
        //edge case
        //        if(degrees >= 360) {
        //            degrees %= 360;
        //        }
        while(true) {
            SampleProvider gyroSample = sensorObject.getAngleMode();
            float[] gyroOut = new float[gyroSample.sampleSize()];
            gyroSample.fetchSample(gyroOut, 0);
            LCD.refresh();
            LCD.drawString("Angle: " + gyroOut[0], 0,0);

            leftMotor.setSpeed(degreesPerSecond);
            rightMotor.setSpeed(degreesPerSecond);

            if(degrees >= 0) {
                leftMotor.forward();
                rightMotor.backward();
            }
            else if(degrees < 0) {
                leftMotor.backward();
                rightMotor.forward();
            }

            if(Math.abs((int)gyroOut[0]) >= Math.abs(degrees)) {
                leftMotor.stop(true);
                rightMotor.stop(true);
                break;
            } 
        }

    }

    public static void main(String[] args) {
        TurningWithGyro gyroFeedback = new TurningWithGyro();
        gyroFeedback.setSpeed(80);
        gyroFeedback.turn(180);
    }



}