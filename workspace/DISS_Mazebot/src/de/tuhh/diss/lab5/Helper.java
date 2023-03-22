package de.tuhh.diss.lab5;
import java.util.Collections;
import java.util.LinkedHashMap;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import de.tuhh.diss.lab4.ProportionalController;

public class Helper {
    EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
    EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S3);
    EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);

    private final double GAIN = 7;  // PI controller gain
    final public static double WHEEL_DIA = 5.4; //in cms
    final public static double GEAR_RATIO = 3;
    final public int GRID_DISTANCE = 35;  //in cms
    final public int TURN_DEGREE = 90;
    public final double OFFSET = 4.5; //in cms

    ProportionalController controller = new ProportionalController(rightMotor, leftMotor, gyroSensor);
    SampleProvider distanceSample = distanceSensor.getDistanceMode();

    /**
     * Calculates the necessary motor angle for a distance
     * motorAngle = (gear ratio* distance * 360)/(circumference of wheel)
     * @params: distance (in cms)
     */
    public static double motorAngle(double distance) {
        double motorTurnangle = Math.abs((distance * 360) / (Math.PI * WHEEL_DIA));
        return motorTurnangle*GEAR_RATIO;
    }

    /**
     * Distance measurement between the robot wheel axis and the wall
     * @return (double) distance in cms
     */
    public double distanceMeasure() {
        float[] dsArray = new float[distanceSample.sampleSize()];
        distanceSample.fetchSample(dsArray, 0);
        double distance = (dsArray[0] * 100); // in cms
        return distance + OFFSET;
    }

    void moveForward(int distance) {
        int leftMotorSpeed = (int) (rightMotor.getMaxSpeed());
        int rightMotorSpeed = (int) (leftMotor.getMaxSpeed());

        double rotations = motorAngle(distance);
        rightMotor.setSpeed(leftMotorSpeed);
        leftMotor.setSpeed(rightMotorSpeed);

        leftMotor.forward();
        rightMotor.forward();
        rightMotor.rotate((int)rotations, true);
        leftMotor.rotate((int)rotations);
        rightMotor.stop();
        leftMotor.stop();
    }

    void moveBackward(int distance) {
        int leftMotorSpeed = (int) (leftMotor.getMaxSpeed());
        int rightMotorSpeed = (int) (rightMotor.getMaxSpeed());

        double rotations = motorAngle(distance);
        rightMotor.setSpeed(leftMotorSpeed);
        leftMotor.setSpeed(rightMotorSpeed);
        leftMotor.forward();
        rightMotor.forward();
        rightMotor.rotate(-(int)rotations, true);
        leftMotor.rotate(-(int)rotations);

        rightMotor.stop();
        leftMotor.stop();

    }

    void rightTurn(int degrees) {
        controller.setGain(GAIN);
        controller.turn(-degrees);
    }

    void leftTurn(int degrees) {
        controller.setGain(GAIN);
        controller.turn(degrees);
    }

    /**
     * Calculates the angle required to rotate the robot to desired direction based on the wall color.
     * LinkedHashmap: Key: Distance (double), Value: String[color,angleCount] 
     * @param map : A LinkedHashmap object which takes double values key and String valued array 
     * @param angleCount : Current rotation count of the robot 
     * @return double[2] 
     */
    double[] angleNeeded(LinkedHashMap<Double, String[]> map, int angleCount) {
        double maxKey = Collections.max(map.keySet());
        String[] desiredcount = map.get(maxKey);
        int desiredAngleCount = Integer.parseInt(desiredcount[1]);
        int desiredRotation = (angleCount - (Integer.parseInt(desiredcount[1]))) * TURN_DEGREE;
        return new double[] {desiredRotation,desiredAngleCount, maxKey};
    }

    /**
     * <b>hintWallHelper</b> function gets called when the color sensor detects a <b>Yellow wall</b>.
     * Upon calling, it checks the distance to the yellow wall,<br>
     *  - If its less than 20, then it turns right and move forward <br>
     *  - Greater than 20, calculates the number of grids it wanted to move to get close to the wall <br>
     * @param wallDistance : Current distance to the wall
     * @param hintDirection : Direction to turn after detecting the hint wall
     * @param angleCount : Current rotation count (out of 4 walls)
     */
    void hintWallHelper(double wallDistance, String hintDirection, int angleCount) {
        if (wallDistance < 20) {
            switch (hintDirection) {
            case "RIGHT": 
                rightTurn(TURN_DEGREE);
                moveBackward(GRID_DISTANCE);
                //additional turn operation is added for generalizing the algorithm to 
                //avoid going back to the visited grid
                leftTurn(TURN_DEGREE);   
                break;
            case "LEFT": 
                leftTurn(TURN_DEGREE);
                moveBackward(GRID_DISTANCE);
                rightTurn(TURN_DEGREE);
                break;
            default: break;
            }
        }
        else {
            //finding ratio: chose int, as it gives rounding down value
            double ratio = wallDistance/GRID_DISTANCE;
            if(angleCount < 4) {
                leftTurn((4 - angleCount)*TURN_DEGREE);
            }
            moveForward((int) ratio * GRID_DISTANCE);
        }
    }

    /**
     * Gets the current and desired count & rotates the robot to face towards the exit direction 
     * @param angleCount : Current rotation count of the robot
     * @param exitSide : desired rotation count where the green wall lies
     * @return void
     */
    void turnTowardsExit(int angleCount, int exitSide) {
        int exit = (angleCount - exitSide) * TURN_DEGREE;
        leftTurn(180 - exit);
    }


}
