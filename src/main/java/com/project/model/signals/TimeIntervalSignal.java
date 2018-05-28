package com.project.model.signals;

import com.project.model.connetions.ConnectionType;

public class TimeIntervalSignal extends Signal {

	public TimeIntervalSignal(ConnectionType type, int fromNeuronNumber, int fromPortNumber, int toNeuronNumber, int toPortNumber) {
		super(type, fromNeuronNumber, fromPortNumber, toNeuronNumber, fromNeuronNumber);
	}
	@Override
	public void setParametrs(int... args) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public Double getValue() {
		if (value.intValue() > 0){
			int temp = value.intValue();
			value = (double) temp--;
			return (double) 1;
		}
		return (double) 0;
	}

}
