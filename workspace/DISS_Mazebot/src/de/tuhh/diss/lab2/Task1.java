package de.tuhh.diss.lab2;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Task1 {

	public static void main(String[] args) {
		
		final double width = LCD.DISPLAY_CHAR_WIDTH;
//		final double depth = LCD.DISPLAY_CHAR_DEPTH;
		
		String string = "Hello World scrolling text";
		int length = string.length();
		
		//LCD.drawString(String, column, row)
		//We don't need to worry about "y" here
		int initial_x = 0;
		while (true) {
			LCD.clear();
			LCD.drawString(string, initial_x, 0);
			LCD.refresh();
			initial_x--;
			if(initial_x == - (length+1)) {
				initial_x = 0;
				break;
			}
			Delay.msDelay(800);
			
		}

	}
}
