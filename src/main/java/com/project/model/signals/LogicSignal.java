package com.project.model.signals;

import com.project.model.connetions.ConnectionType;

public class LogicSignal extends Signal {

	public LogicSignal(ConnectionType type, int fromNeuronNumber, int fromPortNumber, int toNeuronNumber, int toPortNumber) {
		super(type, fromNeuronNumber, fromPortNumber, toNeuronNumber, fromNeuronNumber);
	}

	@Override
	public void setParametrs(int... args) {
		// TODO Auto-generated method stub
	}

	@Override
	public Double getValue() {
		return (Double) value;
	}

	@Override
	public void setValue(Double value) {
//		int temp = value.intValue();
//		if (temp == 1 | temp == 0) {
//			this.value = temp;
//		} else {
//			throw new RuntimeException(value + " isn't logic value!");
//		}
		this.value = (double) (value > 0 ? 1:0);
	}

}
