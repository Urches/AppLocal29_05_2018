package com.project.model.signals;

import com.project.model.connetions.ConnectionType;

import java.util.Objects;

public class Signal implements Valuable {

    public static final Double DEFAULT_VALUE = 0.0;
    public int number;
	
	protected ConnectionType type;
	
	protected Double value;
	
	private Double weight;

	private int toElementNumber;

	private int toPortNumber;

	private int fromElementNumber;

	private int fromPortNumber;

	public Signal(){

	}

	@Deprecated
	public Signal(ConnectionType type, int fromElementNumber, int fromPortNumber, int toElementNumber, int toPortNumber) {
	}

	//abstract
	public void setParametrs(int... args) {
	}

	//abstract
	public void setValue(Double value) {
		this.value = value;
	}

	public int getFromElementNumber() {
		return fromElementNumber;
	}

	public void setFromElementNumber(int fromElementNumber) {
		this.fromElementNumber = fromElementNumber;
	}

	public int getToElementNumber() {
		return toElementNumber;
	}

	public void setToElementNumber(int toElementNumber) {
		this.toElementNumber = toElementNumber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setType(ConnectionType type) {
		this.type = type;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public int getToPortNumber() {
		return toPortNumber;
	}

	public void setToPortNumber(int toPortNumber) {
		this.toPortNumber = toPortNumber;
	}

	public int getFromPortNumber() {
		return fromPortNumber;
	}

	public void setFromPortNumber(int fromPortNumber) {
		this.fromPortNumber = fromPortNumber;
	}

	@Override
	public Double getValue() {
		return value;
	}

	public ConnectionType getType() {
		return type;
	}

//	public void setType(ConnectionTypeEnum type) {
//		this.type = type;
//	}}

//	public void setKeyValue(int value) {
//		this.key = KeysController.SINGLTONE.getKeyOnValue(value, this);
//	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Signal signal = (Signal) o;
		return number == signal.number &&
				toElementNumber == signal.toElementNumber &&
				toPortNumber == signal.toPortNumber &&
				fromElementNumber == signal.fromElementNumber &&
				fromPortNumber == signal.fromPortNumber &&
				type == signal.type &&
				Objects.equals(value, signal.value) &&
				Objects.equals(weight, signal.weight);
	}

	@Override
	public int hashCode() {

		return Objects.hash(number, type, value, weight, toElementNumber, toPortNumber, fromElementNumber, fromPortNumber);
	}

	@Override
	public String toString() {
		return "Signal{" +
				"number=" + number +
				", type=" + type +
				", value=" + value +
				", toElementNumber=" + toElementNumber +
				", toPortNumber=" + toPortNumber +
				", fromElementNumber=" + fromElementNumber +
				", fromPortNumber=" + fromPortNumber +
				'}';
	}
}
