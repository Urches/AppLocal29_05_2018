package com.project.model.functions;

import java.util.Arrays;
import java.util.function.BinaryOperator;

/**
 * Logistic
 */
public class SigmoidFunction extends ActivatedFunction {

    private double t = 0.005;

    /**
     * Mapping of parent values array is:
     * value = sum(values);
     */
    @Override
    public double getResult() {
        double value = Arrays.stream(values).reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        return 1 / (1 + Math.exp(t * value));
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }
}
