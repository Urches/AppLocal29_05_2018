package com.project.model.functions;

public abstract class ActivatedFunction {

	protected Double[] values;

	protected String type;

	public abstract double getResult();
	
	public void setInValues(Double...values){
		this.values = values;
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
