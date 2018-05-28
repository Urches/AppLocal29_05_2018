package com.project.elements.generators;

public class FrequencyGenerator extends Generator {
	
	private final static long DEFAULT_INTERVAL = 50;
	private final static long DEFAULT_LENGTH = 50;
	
	private long interval = DEFAULT_INTERVAL;
	
	private long length = DEFAULT_LENGTH;

	private byte startValue = GND;

	private byte currentValue;

	private boolean isVcc;

	private boolean isGnd;

	@Override
	public Double getValue() {
		long time = this.timer.getCurrentModelTime();
		if (time == 0){
			currentValue = startValue;
		} else if(time % interval == 0 && isVcc){
			currentValue = GND;
		} else if (time % interval == 0 && isGnd){
			currentValue = VCC;
		}
		return  (double) currentValue;
	}

	public void setStart(byte value){
		this.startValue = value;
		this.isGnd = (value== GND);
		this.isVcc = (value== VCC);
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}


}
