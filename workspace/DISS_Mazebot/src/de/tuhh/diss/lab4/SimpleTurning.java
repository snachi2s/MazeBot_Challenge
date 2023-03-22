package de.tuhh.diss.lab4;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class SimpleTurning implements Turner{
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);

    private final double WHEEL_DIAMETER = 5.5; // in cms
    private final double AXIS_WIDTH = 12.3;    //in cms
    private final double GEAR_RATIO = 3.0;
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
        int rotations = motorRotations(degrees);
        leftMotor.setSpeed(degreesPerSecond);
        rightMotor.setSpeed(degreesPerSecond);

        leftMotor.rotate(rotations,true);
        rightMotor.rotate(-rotations);

        leftMotor.stop(true);
        rightMotor.stop(true);        
    }

    /**
     * <b>Method description:</b> Calculates and returns the number of rotations required for the wheels to reach the given angle <br>
     * <b>Input parameters  :</b> degrees (type: int) <br>
     * @return           :</b> int
     */
    private int motorRotations(int degrees) {
        double result = (degrees*AXIS_WIDTH*GEAR_RATIO)/(WHEEL_DIAMETER);
        //        System.out.println(result);
        return (int)(result);
    }

    public static void main(String[] args) {
        SimpleTurning turning = new SimpleTurning();
        turning.setSpeed(80);
        turning.turn(-90);
    }
}