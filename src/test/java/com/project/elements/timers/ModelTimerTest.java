package com.project.elements.timers;

import com.project.model.exceptions.ModelTimerException;
import org.junit.Assert;
import org.junit.Test;

public class ModelTimerTest {

    private ModelSimpleTimer timer = new ModelSimpleTimer();

    @Test
    public void start() {
        timer.start();
        try {
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(0, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(10, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(20, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(30, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
        } catch (ModelTimerException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ModelTimerException.class)
    public void testStarException() {
        Assert.assertEquals(0, timer.getCurrentModelTime());
    }

    @Test
    public void start1() {
        timer.start(20);
        try {
            Assert.assertEquals(0, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(10, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(20, timer.getCurrentModelTime());
            Assert.assertFalse(timer.hasNext());
        } catch (ModelTimerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reset() {
        timer.start();
        try {
            Assert.assertEquals(0, timer.getCurrentModelTime());
            Assert.assertTrue(timer.isStarted());
            timer.reset();
            Assert.assertEquals(0, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertTrue(timer.isStarted());
            Assert.assertEquals(10, timer.getCurrentModelTime());
            timer.reset();
            Assert.assertEquals(0, timer.getCurrentModelTime());
        } catch (ModelTimerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setStep() {
        timer.start();
        try {
            Assert.assertEquals(0, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(10, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(20, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(30, timer.getCurrentModelTime());
            timer.setStep(50);
            Assert.assertEquals(80, timer.getCurrentModelTime());
            Assert.assertTrue(timer.hasNext());
            Assert.assertEquals(130, timer.getCurrentModelTime());
        } catch (ModelTimerException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ModelTimerException.class)
    public void testException() {
        timer.setStep(0);
    }

    @Test
    public void hasNext() {
        Assert.assertFalse(timer.hasNext());
        timer.start(50);

        Assert.assertTrue(timer.hasNext());
        timer.setStep(25);
        Assert.assertEquals(0, timer.getCurrentModelTime());
        Assert.assertTrue(timer.hasNext());
        Assert.assertEquals(25, timer.getCurrentModelTime());
        Assert.assertTrue(timer.hasNext());
        Assert.assertEquals(50, timer.getCurrentModelTime());
        Assert.assertFalse(timer.hasNext());

        timer.reset();
        timer.start(20);
        timer.setStep(10);
        Assert.assertTrue(timer.hasNext());
        Assert.assertEquals(0, timer.getCurrentModelTime());
        Assert.assertTrue(timer.hasNext());
        Assert.assertEquals(10, timer.getCurrentModelTime());
        Assert.assertTrue(timer.hasNext());
        Assert.assertEquals(20, timer.getCurrentModelTime());
        Assert.assertFalse(timer.hasNext());
    }


    @Test
    public void testGetCurrentModelTime() {
        //TODO!!!
    }
}