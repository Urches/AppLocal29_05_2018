package com.project.model.functions;

import java.util.Arrays;

public class LinearBounderFunction extends ActivatedFunction {

	private double bottom = -1;
	private double top = 1;

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	@Override
	public double getResult() {
		double value = Arrays.stream(values).reduce(Double::sum).get();

		if (value >= bottom || value <= top) {
			return value;
		} else if (value > top) {
			return top;
		} else if (value < bottom) {
			return bottom;
		}
		return value;
	}
}
