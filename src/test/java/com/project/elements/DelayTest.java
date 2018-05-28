package com.project.elements;

import org.junit.Before;

import static org.junit.Assert.*;

public class DelayTest {

    @Before
    public void setUp(){
        Delay delay = new Delay();
        delay.setDelayTime(20);
    }
}