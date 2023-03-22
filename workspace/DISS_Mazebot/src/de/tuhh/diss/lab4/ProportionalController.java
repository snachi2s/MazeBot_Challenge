package de.tuhh.diss.lab4;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class ProportionalController implements Turner {
    
    private EV3LargeRegulatedMotor rightMotor;
    private EV3LargeRegulatedMotor leftMotor;
    private EV3GyroSensor sensorObject;
    private double gain;

    public ProportionalController(EV3LargeRegulatedMotor rightMotor, EV3LargeRegulatedMotor leftMotor, EV3GyroSensor gyroSensor) {
        this.rightMotor = rightMotor;
        this.leftMotor = leftMotor;
        this.sensorObject = gyroSensor;
    }
   
    private int motorSpeed;
    
    public void setGain(double gain) {
        this.gain = gain;
    }

    @Override
    public void setSpeed(int degreesPerSecond) {
        if(degreesPerSecond < 0) {
            this.motorSpeed = -degreesPerSecond;
        }
        else {
            this.motorSpeed = degreesPerSecond;
        }
    }
    /**
     * <b>Method description :</b> Implements Proportional controller for setting the motor Speed <br>
     * <b>Formula description:</b> motorSpeed = K_p * (thetaDesired - thetaActual) <br>
     * <b>input              :</b> degrees (type: int)
     * @return None
     */
    @Override
    public void turn(int degrees) {
        while(true) {
            LCD.refresh();
            SampleProvider gyroSample = sensorObject.getAngleMode();
            float[] gyroAngle = new float[gyroSample.sampleSize()];
            gyroSample.fetchSample(gyroAngle, 0);
//            LCD.drawString("Angle:" + gyroAngle[0], 0, 0);

            int delta = degrees - (int)gyroAngle[0];
            motorSpeed = (int)(gain * delta);

            setSpeed(motorSpeed);

            leftMotor.setSpeed(motorSpeed);
            rightMotor.setSpeed(motorSpeed);

            if(degrees >= 0) {
                leftMotor.forward();
                rightMotor.backward();
            }
            else if(degrees < 0) {
                leftMotor.backward();
                rightMotor.forward();
            }

            if((int)gyroAngle[0] == degrees) {
                leftMotor.stop(true);
                rightMotor.stop(true);
                break;
            } 
        }    
    }

    public static void main(String[] args) {
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3GyroSensor sensorObject = new EV3GyroSensor(SensorPort.S3);
        ProportionalController controller = new ProportionalController(rightMotor, leftMotor, sensorObject);
        
        controller.setGain(7.0);
        controller.setSpeed(250);
        controller.turn(180);
    }


}