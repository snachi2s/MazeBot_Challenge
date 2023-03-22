package de.tuhh.diss.lab5;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class StartUpMenu {
    private int exitColor = 0;
    private int hintColor = 0;
    private int hintDirection = 0;
    private int choice = 0;
    private boolean startFlag = false;

    String[] colors = {"RED", "GREEN", "BLUE", "YELLOW"};
    String[] direction = {"LEFT", "RIGHT"};

    /**
     * Sets up the startup menu to configure the parameters <br>
     * <b> Output array : String{hintColor, hintDirection, exitColor, startFlag} </b>
     * @return String[] 
     */
    public String[] menu() {
        while(!startFlag) {
            LCD.clear();
            LCD.drawString("HINT_COLOR: " + colors[hintColor], 1, 0);
            LCD.drawString("HINT_DIRECTION " + direction[hintDirection], 1, 1);
            LCD.drawString("EXIT_COLOR " + colors[exitColor], 1, 2);
            LCD.drawString("Start", 1, 3);
            LCD.drawChar('>', 0, choice);
            int button = Button.waitForAnyPress();

            switch(button) {

            case Button.ID_ENTER:
                if(choice == 3) {
                    startFlag = true;
                    break;
                }
                else {
                    break;
                }

            case Button.ID_ESCAPE:
                choice = 0;
                hintColor = 0;
                hintDirection = 0;
                exitColor = 0;
                break;

            case Button.ID_UP:
                if (choice > 0 & choice <= 3) {
                    choice--;
                }
                break;

            case Button.ID_DOWN:
                if (choice < 3 & choice >= 0) {
                    choice++;
                }
                break;

            case Button.ID_RIGHT:
                switch(choice) {
                case 0:
                    if(hintColor < 3) {
                        hintColor++;
                    }
                    break;
                case 1: 
                    if(hintDirection < 1) {
                        hintDirection++;
                    }               
                    break;                 
                case 2:                      
                    if(exitColor < 3) {
                        exitColor++;
                    }                          
                    break;
                }
                break;

            case Button.ID_LEFT:        
                switch(choice) {      
                case 0:       
                    if(hintColor > 0) {
                        hintColor--;
                    } 
                    break;

                case 1: 
                    if(hintDirection > 0) {
                        hintDirection--;
                    }
                    break;

                case 2: 
                    if(exitColor > 0) {
                        exitColor--;
                    }
                    break;
                }
                break;
            default: break;
            }

        }
        return new String[] {colors[hintColor], direction[hintDirection], 
                colors[exitColor], Boolean.toString(startFlag)};
    }
}
