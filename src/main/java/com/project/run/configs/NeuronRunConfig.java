package com.project.run.configs;

import java.util.HashMap;
import java.util.Map;

public class NeuronRunConfig {

    private int targetNumber;
    
    private Map<String, String> properties = new HashMap<>();
    
    private Map<Integer, Boolean> observedPorts = new HashMap<>();
    
    public int getTargetNumber() {
	return targetNumber;
    }
    
    public String getProperty(String key){
	return properties.get(key);
    }

    public Map<Integer, Boolean> getObservedPorts() {
	return observedPorts;
    }

    public void setObservedPorts(Map<Integer, Boolean> observedPorts) {
	this.observedPorts = observedPorts;
    }
}
