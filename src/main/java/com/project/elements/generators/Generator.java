package com.project.elements.generators;

import com.project.elements.timers.ModelTimer;
import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.connetions.Port;
import com.project.model.exceptions.ModelTimerException;
import com.project.model.signals.Signal;

import java.util.Objects;

public abstract class Generator extends Element {

	public final static Double DEFAULT_VALUE = 1.0;
	public final static byte VCC = 1;
	public final static byte GND = 0;

	protected ModelTimer timer;

	public Generator(){
		this.type = ElementType.GENERATOR;
	}

	public abstract Double getValue();

	public ModelTimer getTimer() {
		return timer;
	}

	public void setTimer(ModelTimer timer) {
		this.timer = timer;
	}

	@Override
	public String toString() {
		return "Generator{" +
				"number=" + number +
				", ports=" + ports +
				", type=" + type +
				'}';
	}
}
