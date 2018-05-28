package com.project.model.connetions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PortTest {
	
	private Port port;
	
	@Before
	public void setUp(){
		port = new Port();
		port.setNumber(1);
		port.setPosition(PortPosition.IN);
	}
	
	@Test
	public void portNumber(){
		Assert.assertTrue(port.getNumber() > 0);
	}
	
	@Test
	public void portPositionDefault(){
		
		Assert.assertEquals(PortPosition.IN, port.getPosition());
	}
	
	@Test
	public void portPositionOut(){
		Port port = new Port();
		port.setNumber(1);
		port.setPosition(PortPosition.OUT);
		Assert.assertEquals(PortPosition.OUT, port.getPosition());
	}
	
	@Test
	public void portPositionNotNull(){
		Assert.assertNotNull(port.getPosition());
	}
}
