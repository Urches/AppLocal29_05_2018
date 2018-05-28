package com.project.elements.generators;

import com.project.elements.timers.ModelTimer;
import com.project.model.exceptions.ModelTimerException;
import com.project.model.signals.Signal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AsymmetricalGeneratorTest {

    private  AsymmetricalGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new AsymmetricalGenerator();
        ModelTimer timer = new ModelTimer() {

            long value = 0;

            @Override
            public void start() {

            }

            @Override
            public void reset() {

            }

            @Override
            public void stop() {

            }

            @Override
            public long getStep() {
                return 0;
            }

            @Override
            public void setStep(long step) {

            }

            @Override
            public long getFinishTime() {
                return 0;
            }

            @Override
            public void setFinishTime(long finishTime) {

            }

            @Override
            public boolean isStarted() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public long getLastTime() {
                return 0;
            }

            @Override
            public void setLastTime(long lastTime) {

            }

            @Override
            public long getCurrentModelTime() throws ModelTimerException {
                long ret = value;
                value += 10;
                return ret;
            }
        };
        generator.setTimer(timer);
    }

    @Test
    public void setDefaultValue() {
        Assert.assertEquals(Generator.DEFAULT_VALUE, generator.getValue());
        generator.setDefaultValue(20.0);
        Assert.assertEquals(new Double(20.0), generator.getValue());
        generator.setDefaultValue(5.0);
        Assert.assertEquals(new Double(5.0), generator.getValue());
    }

    @Test
    public void addValue() {
        generator.addValue(0, 20, 5.0);
        generator.addValue(20, 30, 2.0);
        Assert.assertEquals(new Double(5.0), generator.getValue());
        Assert.assertEquals(new Double(5.0), generator.getValue());
        Assert.assertEquals(new Double(2.0), generator.getValue());
        Assert.assertEquals(Generator.DEFAULT_VALUE, generator.getValue());
    }
}