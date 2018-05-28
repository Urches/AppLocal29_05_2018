package com.project.run.executors;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.model.connetions.Port;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Valuable;
import com.project.run.configs.NeuronRunConfig;

@Deprecated
public class NeuronRunExecutor {

    private Neuron neuron;

    private Map<Integer, Optional<Valuable>> neuronValues;

    private Integer[] portsNum;

    public NeuronRunExecutor(Neuron neuron, NeuronRunConfig neuronConfig,
	    Map<Integer, Optional<Valuable>> neuronValues) {
	this.neuron = neuron;
	this.neuronValues = neuronValues;
	portsNum = neuron.getInPorts().stream().map(p -> p.getNumber()).toArray(Integer[]::new);

	//Get observed ports
	Set<Port> observedPorts = neuronConfig.getObservedPorts().entrySet().stream()
		.filter(e -> e.getValue() == true)
		.map(p -> neuron.getPort(p.getKey()))
		.collect(Collectors.toSet());

    }

    public Double execute() {
	Double[] args = Arrays.stream(portsNum).map(portNum -> neuronValues.get(portNum).get().getValue())
		.toArray(Double[]::new);

	//neuron.getActivatedFunction().setParametrs(args);
	Double result = neuron.getActivatedFunction().getResult();
	return result;
    }

    /**
     * Temp
     * 
     * @param inMap
     * @return
     */
    private String mapToString(Map<Integer, Optional<Valuable>> inMap) {
	return inMap.entrySet().stream()
		.map(entry -> "Key: " + entry.getKey() + " " + "Value " + entry.getValue().get().getValue())
		.collect(Collectors.joining(" ;"));
    }

}
