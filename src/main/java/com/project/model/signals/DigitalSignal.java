package com.project.model.signals;

import com.project.model.connetions.ConnectionType;

public class DigitalSignal extends Signal {

	private Integer bit;

	public DigitalSignal(ConnectionType type, int fromNeuronNumber, int fromPortNumber, int toNeuronNumber, int toPortNumber) {
		super(type, fromNeuronNumber, fromPortNumber, toNeuronNumber, fromNeuronNumber);
	}
	
	@Override
	public void setParametrs(int... args) {

	}

	@Override
	public void setValue(Double value) {
		this.value = (double) value.intValue();
		this.bit = Integer.bitCount(this.value.intValue());
	}

	@Override
	public Double getValue() {
		return (Double) value;
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Signal [bit=");
		builder.append(bit);
		builder.append(" ");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
