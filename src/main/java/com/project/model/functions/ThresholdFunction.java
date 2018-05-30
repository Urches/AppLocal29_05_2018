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
	 * value = sum(values);
	 */
	@Override
	public double getResult() {
		double value = Arrays.stream(values).reduce(Double::sum).get();
		return q < value ? 1 : 0;
	}
}
