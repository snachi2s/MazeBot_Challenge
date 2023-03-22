package de.tuhh.diss.lab2;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class Task3 {
	public static int volume = 50;
	public static int frequency = 400;
    public static int duration = 500;
	
    //method that generates a beep
	public static void playbeep(int setVolume, int setFrequency, int setDuration) {
		Sound.setVolume(setVolume);
		Sound.playTone(setFrequency,setDuration);
	}
	
	public static void main(String[] args) {
		
	    int choice = 0;
	    while (true) {
	    	LCD.refresh();
	    	LCD.clear();
	    	LCD.drawString("Menu", 1, 0);
	        LCD.drawString("Volume " + Integer.toString(volume) , 1, 1);
	        LCD.drawString("Frequency " + Integer.toString(frequency), 1, 2);
	        LCD.drawString("Duration " + Integer.toString(duration), 1, 3);
	        LCD.drawString("Play", 1, 4);
	        LCD.drawChar('>',0 , choice);
	        int button = Button.waitForAnyPress();
	        try {
		        switch (button) {
		            case Button.ID_UP:
		                if (choice > 0 & choice <= 4) {
		                    choice--;
		                }
		                break;
		            case Button.ID_DOWN:
		                if (choice < 4 & choice >= 0) {
		                    choice++;
		                }
		                break;
		            case Button.ID_LEFT:
		                switch (choice) {
		                    case 1:
		                    	if (volume > 5) {
		                            volume -= 5;
		                        }
		                        break;
		                    case 2:
		                        if (frequency > 40) {
		                            frequency -= 40;
		                        }
		                        break;
		                    case 3:
		                        if (duration > 50) {
		                            duration -= 50;
		                        }
		                        break;
		                }
		                break;
		            case Button.ID_RIGHT:
		                switch (choice) {
		                    case 1:
		                    	if (volume <= 45 ) {
		                            volume += 5;
		                        }
		                        break;
		                    case 2:
		                    	if (frequency <= 360) {
		                            frequency += 40;
		                        }
		                        break;
		                    case 3:
		                        if (duration <= 450) {
		                            duration += 50;
		                        }
		                        break;
		                }
		                break;
		                
		            case Button.ID_ENTER:
		                if (choice == 4) {
		                    playbeep(volume,frequency,duration);
		                }
		                break;
		                
		            case Button.ID_ESCAPE:
		                break;
		                
		            default:
		            	break;
		        }
	            LCD.refresh();
		    }
	        catch(Exception e){
	        	LCD.drawString("Unexpected input", 1,8);
	        }
	    }
	    
	}
}
