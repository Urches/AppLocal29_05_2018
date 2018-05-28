package com.project.model.functions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TresholdFunctionTest {

    ThresholdFunction function;

    @Before
    public void setUp() throws Exception {
        function = new ThresholdFunction();
    }

    @Test
    public void testGetResultDefault(){
        function.setInValues(10.0);
        Assert.assertEquals(1.0, function.getResult(), 0.001);

        function.setInValues(5.0);
        Assert.assertEquals(1.0, function.getResult(), 0.001);

        function.setInValues(0.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-5.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-10.0, 10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-5.0, 10.0);
        Assert.assertEquals(1.0, function.getResult(), 0.001);

        function.setInValues(-12.0, 10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);
    }

    @Test
    public void testGetResultQ(){
        function.setQ(10.0);
        function.setInValues(10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(0.0, 10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(5.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(0.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-5.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-10.0, 15.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);

        function.setInValues(-5.0, 10.0);
        Assert.assertEquals(0.0, function.getResult(), 0.001);
    }
}