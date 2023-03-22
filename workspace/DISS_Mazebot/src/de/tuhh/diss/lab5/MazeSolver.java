package de.tuhh.diss.lab5;
import java.util.LinkedHashMap;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MazeSolver {

    public int angleCount = 0;
    public int firstTimeFlag = 0;
    public int exitFlag = 0;
    public int exitSide = 0;

    Helper mazeHelper = new Helper();
    ColorDetectionTest color = new ColorDetectionTest();

    void solving(String hintColor, String hintDirection, String exitColor) {
        firstTimeFlag++;
        LinkedHashMap<Double, String[]> distanceColorAngleMap = new LinkedHashMap<Double,String[]>(4);

        while(true) {
            firstTimeFlag++;
            String detectedColor = color.getColor();
            double wallDistance = mazeHelper.distanceMeasure();
            LCD.clear();
            LCD.drawString("Wall color: " + detectedColor, 0, 1);
            LCD.drawString("Distance: " + String.format("%.2f", wallDistance), 0, 3);
            angleCount += 1;
            distanceColorAngleMap.put(wallDistance, new String[] {detectedColor, Integer.toString(angleCount)});

            if (distanceColorAngleMap.size() == 4) {
                if(detectedColor.equals(exitColor)) {
                    exitSide = angleCount;
                    mazeHelper.turnTowardsExit(angleCount, exitSide);
                    Sound.playTone(2000, 1000);
                    LCD.drawString("EXIT FOUND!!!!", 0, 5);
                    break;
                }
                else if (exitFlag == 1) {
                    mazeHelper.turnTowardsExit(angleCount, exitSide);
                    Sound.playTone(2000, 1000);
                    LCD.drawString("EXIT FOUND!!!!", 0, 5);
                    break;
                }
                else {
                    double[] rotateArray = mazeHelper.angleNeeded(distanceColorAngleMap, angleCount);
                    if(firstTimeFlag == 5) {
                        mazeHelper.leftTurn(180- (int)rotateArray[0]);
                        mazeHelper.moveForward(mazeHelper.GRID_DISTANCE);
                        distanceColorAngleMap.clear();
                        angleCount = 0;
                    }

                    else if(firstTimeFlag > 5 && rotateArray[1] == 1) {
                        distanceColorAngleMap.remove(rotateArray[2]);
                        double[] modifiedRotation = mazeHelper.angleNeeded(distanceColorAngleMap, angleCount);
                        mazeHelper.leftTurn(180 - (int)modifiedRotation[0]);
                        mazeHelper.moveForward(mazeHelper.GRID_DISTANCE);
                        distanceColorAngleMap.clear();
                        angleCount = 0;
                    }
                    //edge case
                    else {
                        mazeHelper.leftTurn(180- (int)rotateArray[0]);
                        mazeHelper.moveForward(mazeHelper.GRID_DISTANCE);
                        distanceColorAngleMap.clear();
                        angleCount = 0; 
                    }
                }
            } //hashmap full loop ending

            else {
                if (detectedColor.equals(exitColor) && exitFlag != 1) {
                    exitFlag = 1;
                    exitSide = angleCount;
                    mazeHelper.leftTurn(mazeHelper.TURN_DEGREE);
                }

                /*
                 * Hint wall decision
                 */
                else if(detectedColor.equals(hintColor)) {
                    mazeHelper.hintWallHelper(wallDistance, hintDirection, angleCount);                    
                    distanceColorAngleMap.clear();
                    angleCount = 0;
                } 

                /*
                 * default turning
                 */
                else {
                    mazeHelper.leftTurn(mazeHelper.TURN_DEGREE);
                }
            }
            mazeHelper.gyroSensor.reset();

        } //while loop closure
    }
    public static void main(String[] args) {
        MazeSolver solver = new MazeSolver();
        StartUpMenu configure = new StartUpMenu();

        String[] start = configure.menu();
        if(start[3].equals("true")) {
            LCD.clear();
            Sound.playTone(1500, 1000);
            solver.solving(start[0], start[1], start[2]);
        }  

        /* Uncomment the following lines of code for simulation 
         * Maze variables set as per lab5 sheet */

        //        MazeSolver solver = new MazeSolver();
        //        final String HINT_COLOR = "YELLOW";
        //        final String HINT_DIRECTION = "RIGHT";
        //        final String EXIT_DIRECTION = "GREEN";
        //        Sound.playTone(1500, 1000);
        //        solver.solving(HINT_COLOR, HINT_DIRECTION, EXIT_DIRECTION);
    }

}
