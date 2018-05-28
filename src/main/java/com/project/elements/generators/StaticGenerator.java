package com.project.elements.generators;

import com.project.model.exceptions.LogicException;

public class StaticGenerator extends Generator {

    private Double value;

    public void setValue(Double value) {
        if(value >= 0){
            this.value = value;
        } else {
            throw new LogicException("Value cannot be set like " + value);
        }
    }

    @Override
    public Double getValue() {
        return value;
    }
}
