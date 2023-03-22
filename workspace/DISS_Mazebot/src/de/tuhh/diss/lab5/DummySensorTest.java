package de.tuhh.diss.lab5;

/**
 * Test example for DummySensor.
 * 
 * @author Christian Busse
 */
import java.util.Locale;

public class DummySensorTest {
    private static final int NUM_SAMPLES = 100;
    private static final int RSD_PERCENT = 2; //relative standard deviation 
    
    static float trueVal = 5.0f;
    static float measVal;
    static double variance;
    
    public static double getVariance(int percentage) {
        return Math.pow(trueVal*percentage/100, 2);
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);       

        /* init dummy sensor */ 
        DummySensor dummy = new DummySensor(true);
        dummy.setTrueValue(trueVal);
        dummy.setVariance(RSD_PERCENT);
        float[] sample = new float[dummy.sampleSize()];
        
        /* generate samples from the dummy sensor */
        for (int idx = 0; idx < NUM_SAMPLES; idx++) {           
            dummy.fetchSample(sample, 0);
            measVal = sample[0];

            System.out.println(String.format("measured = %.2f", measVal));
        }
    }
}
