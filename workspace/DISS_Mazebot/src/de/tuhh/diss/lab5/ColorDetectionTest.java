package de.tuhh.diss.lab5;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorDetectionTest {
    EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);  

    public String getColor(){
        final float GREEN_THRESHOLD = 0.1f;
        final float YELLOW_THRESHOLD = 0.1f;
        final float RED_THRESHOLD = 70.0f;
        final float BLUE_THRESHOLD = 60.0f;

        SensorMode rgbSensor = colorSensor.getRGBMode();
        LowpassFilter lowpass = new LowpassFilter(rgbSensor,(float)0.15, (float)0.75);

        float[] sample = new float[3];
        lowpass.fetchSample(sample);

        //Just to have a whole number value for fixing the threshold value easily
        float red = sample[0]*255;
        float green = sample[1]*255;
        float blue = sample[2]*255;

        double firstMax = Math.max(Math.max(red, blue), green);

        if (red == green && red == blue) {
            return "NONE";
        }
        else {
            if (firstMax == red && firstMax !=0) {
                if (green > blue) {
                    return "YELLOW";
                }
                else if(red > RED_THRESHOLD && blue >= green && firstMax !=0 && (red - blue) > 10) {
                    return "RED";
                }
            }
            else if (green == firstMax && blue >= red && firstMax !=0) {
                return "GREEN";
            }
            else if (blue > BLUE_THRESHOLD && firstMax != 0 && green >= red) {
                return "BLUE";
            } 

            else {
                return "NONE";
            }
        }
        return "NONE";
    }

    public static void main(String[] args) {
        ColorDetectionTest color = new ColorDetectionTest();
        String detectedColor = color.getColor();
        LCD.drawString(" "+ detectedColor, 0, 5);
    }
}