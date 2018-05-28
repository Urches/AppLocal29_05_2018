package com.project.model.functions;


import java.util.Arrays;

public class ThresholdFunction extends ActivatedFunction {

	private double q = 0;

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}

	/**
	 * Mapping of parent values array is:
	 * value = values[0];
	 * q = values[1];
	 */
	@Override
	public double getResult() {
		double value = Arrays.stream(values).reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
		return q < value ? 1 : 0;
	}
}
