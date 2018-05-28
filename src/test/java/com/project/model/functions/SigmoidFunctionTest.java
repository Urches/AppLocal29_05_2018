package com.project.model.functions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SigmoidFunctionTest {

    SigmoidFunction function;

    @Before
    public void setUp() throws Exception {
        function = new SigmoidFunction();
    }

    @Test
    public void testGetResultValues() {
        function.setInValues(10.0, 10.0);
        System.out.println(function.getResult());
        Assert.assertEquals(0.4750208, function.getResult(), 0.001);
    }

    @Test
    public void testGetResultSetT() {
        function.setT(0.008);
        function.setInValues(10.0, 90.0);
        System.out.println(function.getResult());
        Assert.assertEquals(0.310025, function.getResult(), 0.001);
    }
}