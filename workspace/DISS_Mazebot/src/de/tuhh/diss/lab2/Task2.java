package de.tuhh.diss.lab2;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Task2 {
	
	final public static double WHEEL_DIA = 5.4; //in cm
	final public static double GEAR_RATIO = 3;
	/*
	 * method description: calculating the necessary motor angle for a distance d
	 * motorAngle = (gear ratio* distance * 360)/(circumference of wheel)
	 * @params: distance (in cms)
	 */
	public static double motorAngle(double distance) {
		double motorTurnangle = Math.abs((distance * 360) / (Math.PI * WHEEL_DIA));
		return motorTurnangle*GEAR_RATIO;
	}

	public static void main(String[] args) {
		try {
			EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
			EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
			int distance = 20;
			double angleMotor = motorAngle(distance);
			LCD.drawInt((int)angleMotor,1,1);
			
//			LCD.drawString("Distance: " + distance,0,1);
//			int button = Button.waitForAnyPress();			
			
			rightMotor.setSpeed(100);
			leftMotor.setSpeed(100);
			/* 
			 * The methods rotate(int angle) and rotateTo(int ange) use the tachometer 
			 * to control the position at which the motor stops, usually within 1 degree or 2. 
			 */
			leftMotor.forward();
			rightMotor.forward();
			
			rightMotor.rotate((int)angleMotor,true);
			leftMotor.rotate((int)angleMotor);
			
	//		while(leftMotor.isMoving()) {
	//			int a = leftMotor.getTachoCount();
	//			LCD.drawInt(a, 5, 1);
	//		}
			
			rightMotor.stop();
			leftMotor.stop();
			
			leftMotor.close();
			rightMotor.close();
	}
		catch(Exception e) {
			System.out.println("Unexpected error");
		}
	}
}
