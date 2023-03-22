package de.tuhh.diss.lab5;

/**
 * Run a test on the LowpassFilter Class.
 * 
 * @author Christian Busse
 */
import java.util.Locale;

import lejos.robotics.SampleProvider;

public class LowpassFilterTest {

    private static final int NUM_SAMPLES = 500;
    private static final int RSD_PERCENT = 5;

    final static float CUTOFF_FREQUENCY = 3.0f;
    final static float SAMPLING_FREQUENCY = 100.0f;
    final static float FILTER_ALPHA = 0.15f;  

    static float trueValue = 50.0f;
    static float measuredValue;
    static float filteredValue;
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        
        /* init dummy sensor */ 
        DummySensor dummy = new DummySensor(true);
        dummy.setTrueValue(trueValue);
        dummy.setVariance(RSD_PERCENT);
        float[] sample = new float[dummy.sampleSize()];
        
        /* init lowpass filter */
//        LowpassFilter lowpass = new LowpassFilter(dummy, FILTER_ALPHA);     
        
        /**
         * @TODO Uncomment the next line and the test the implementation using the overloaded constructor.
         */
        LowpassFilter lowpass = new LowpassFilter(dummy, CUTOFF_FREQUENCY, SAMPLING_FREQUENCY);        
        
        
        /* run sampling loop */
        float[] filteredSamples = new float[lowpass.sampleSize()];
        for (int idx = 0; idx < NUM_SAMPLES; idx++) {           
            dummy.fetchSample(sample, 0);
            measuredValue = sample[0];
            lowpass.fetchSample(filteredSamples); 
            filteredValue = filteredSamples[0];
            System.out.println(String.format("measured = %.2f, filtered = %.2f",
                                                measuredValue, filteredValue));
        }
    }
}
