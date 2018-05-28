package com.project.run.configs;

import com.project.elements.generators.Generator;

public class GeneratorConfig {

    private Generator generator;
    
    private int neuronNum;
    
    private int portNum;

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public int getNeuronNum() {
        return neuronNum;
    }

    public void setNeuronNum(int neuronNum) {
        this.neuronNum = neuronNum;
    }

    public int getPortNum() {
        return portNum;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }
    
    
}
