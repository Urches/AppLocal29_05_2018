package com.project.run.configs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComponentRunConfig extends DefaultComponentRunConfig {

    private Map<String, String> properties = new HashMap<>();
    
    private int[] executionOrder;

    private Set<NeuronRunConfig> neuronsConfig = new HashSet<>();
    
    private Set<GeneratorConfig> generatorsConfig = new HashSet<>(); 
    
    public String getProperty(String key){
	return properties.get(key);
    }
    
    public Set<NeuronRunConfig> getNeuronConfigs(){
	return neuronsConfig;
    }

    public int[] getExecutionOrder() {
	return executionOrder;
    }

    public Set<GeneratorConfig> getGeneratorsConfig() {
        return generatorsConfig;
    }

    public NeuronRunConfig getNeuronConfig(int number) {
	return neuronsConfig.stream().filter(n -> n.getTargetNumber() == number).findFirst().get();
    }
}
