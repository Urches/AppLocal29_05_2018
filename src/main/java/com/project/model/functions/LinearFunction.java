package com.project.model.functions;

import static com.project.model.connetions.ConnectionType.*;

import com.project.features.FunctionContract;

import java.util.Arrays;


public class LinearFunction extends ActivatedFunction {

    private double k = 1;

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    @Override
    public double getResult() {
        double value = Arrays.stream(values).reduce(Double::sum).get();
        return value * k;
    }

    public static FunctionContract getContrat() {
        FunctionContract contract = new FunctionContract();
        contract.add(DIGITAL);
        contract.add(DIGITAL);
        return contract;
    }
}
