package de.tuhh.diss.lab5;

import java.util.Random;
import lejos.robotics.SampleProvider;

/**
 * A fake lejos sensor for offline development and testing.
 * 
 * @author Christian Busse
 *
 */
public class DummySensor implements SampleProvider {
    
    private double trueVal;
    private double measVal; //"measured" value
    private double variance;
    private boolean enableNoise = false;
    Random rand = new Random();
    
    public DummySensor() {}

    public DummySensor(boolean enableNoise) {       
        this.enableNoise = enableNoise;
    }
    
    public DummySensor(boolean enableNoise, double variance) {      
        this.enableNoise = enableNoise;
        this.variance = variance;
    }
    
    @Override
    public int sampleSize() {
      return 1;
    }
    
    @Override
    public void fetchSample(float[] sample, int offset) {
        if (this.enableNoise == true) {
            measVal = this.trueVal + rand.nextGaussian() * Math.sqrt(variance);
            sample[offset] = (float) measVal;
        } else {
            sample[offset] = (float) this.trueVal;
        }
    }

    public void setTrueValue(double trueVal) {
        this.trueVal = trueVal;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }
    
    public void setVariance(int percentage) {
        this.variance = Math.pow(this.trueVal*percentage/100, 2);
    }
   

}
