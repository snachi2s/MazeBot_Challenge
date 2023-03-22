package de.tuhh.diss.lab5;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class LowpassFilter extends AbstractFilter {
    private float alpha;
    private float[] previousSamples;
    private int offset = 0;

    public LowpassFilter(SampleProvider source, float alpha) {
        super(source);
        this.alpha = alpha;
        previousSamples = new float[super.sampleSize];
    }

    public LowpassFilter(SampleProvider source, float cutoffFrequency, float samplingFrequency) {
        super(source);
        this.alpha = getAlpha(cutoffFrequency, samplingFrequency); 
        previousSamples = new float[super.sampleSize];
    }

    public void fetchSample(float[] samples) {
        float[] newSamples = new float[super.sampleSize];
        source.fetchSample(newSamples, offset);


        /* perform filtering on the sample array */
        /* samples is an empty array (at first) for storing the filtered output--> (y_k)
         * newSamples is the present sample fetched (u_k)
         * y(k-1) is the history --> previous samples
         */
        for (int i = 0; i < super.sampleSize; i++) {
            samples[i] = (1 - alpha) * previousSamples[i] + alpha * newSamples[i];
            previousSamples[i] = samples[i];
        }

    }

    public float getAlpha(float cutoffFrequency, float samplingFrequency) {  
        return 1 / (1 + (float)(2 * Math.PI * cutoffFrequency / samplingFrequency));        
    }
}

