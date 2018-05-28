package com.project.elements.generators;

import com.project.model.exceptions.LogicException;
import com.project.model.signals.Signal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StaticGeneratorTest {

    private StaticGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new StaticGenerator();
    }

    @Test
    public void setValue() {
        generator.setValue(10.0);
        Assert.assertEquals(new Double(10.0), generator.getValue());
        generator.setValue(5.5);
        Assert.assertEquals(new Double(5.5), generator.getValue());
        generator.setValue(0.0);
        Assert.assertEquals(new Double(0.0), generator.getValue());
        try {
            generator.setValue(-6.0);
            Assert.fail();
        } catch (LogicException e) { }
    }

    @Test(expected = LogicException.class)
    public void testLogicException(){
        generator.setValue(-6.0);
    }


    @Test
    public void getValue() {
    }
}