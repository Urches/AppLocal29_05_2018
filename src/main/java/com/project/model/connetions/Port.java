package com.project.model.connetions;

import com.project.model.Element;
import com.project.model.signals.Signal;

public class Port {
	
	public int number;
	
	private PortPosition position;

	private ConnectionType type;

	private boolean observed;

	private Element element;

	public ConnectionType getType() {
		return type;
	}

	public void setType(ConnectionType type) {
		this.type = type;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public boolean isObserved() {
		return observed;
	}

	public void setObserved(boolean observed) {
		this.observed = observed;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setPosition(PortPosition position) {
		this.position = position;
	}

	public int getNumber() {
		return number;
	}
	
	public PortPosition getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "Port{" +
				"number=" + number +
				", position=" + position +
				", type=" + type +
				", observed=" + observed +
				'}';
	}
}
