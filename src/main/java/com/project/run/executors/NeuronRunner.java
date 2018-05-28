package com.project.run.executors;

import com.project.elements.timers.ModelTimer;
import com.project.model.component.Component;
import com.project.model.component.ComponentManager;
import com.project.model.connetions.Port;
import com.project.model.functions.ActivatedFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NeuronRunner implements ElementRunner {

    private Neuron neuron;
    private ModelTimer timer;
    private Component component;

    ComponentManager manager = new ComponentManager();


    public void setComponent(Component component) {
        manager.setComponent(component);
        this.component = component;
    }

    public NeuronRunner(Neuron neuron) {
        this.neuron = neuron;
    }

    @Override
    public Set<RunValue> execute() {
        manager.setComponent(component);
        Set<RunValue> runValues = new LinkedHashSet<>();
        runValues.addAll(preExecute());
        Double outValue = calculateOutValue();
        runValues.addAll(postExecute(outValue));
        return runValues;
    }

    /**
     * @return run values of in ports
     */
    private Set<RunValue> preExecute(){
        return getPortsRunValues(this.neuron.getInPorts(), false, null);
    }

    private Double calculateOutValue(){
        ActivatedFunction af = this.neuron.getActivatedFunction();

        Double[] values = this.neuron.getInPorts().stream()
                .map(port -> {
                    Set<Signal> signals = manager.getSignals(neuron.getNumber(), port.getNumber(), false);
                    if(signals != null && !signals.isEmpty()){
                         if(signals.size() > 1){
                            System.out.println("something wrong!");
                        }
                        Double value = signals.iterator().next().getValue();
                        return value != null ? value : Signal.DEFAULT_VALUE;
                    } else {
                        return Signal.DEFAULT_VALUE;
                    }
                })
                .toArray(Double[]::new);

        System.out.println("Neuron port signals values: " + Arrays.toString(values));
        af.setInValues(values);
        return af.getResult();
    }

    /**
     * Set values of out ports linked signals
     * or create a new one and set value
     * @return run values of out ports
     */
    private Set<RunValue> postExecute(Double calculatedValue){

        this.neuron.getOutPorts().forEach(port-> {
            Set<Signal> signals = manager.getSignals(neuron.getNumber(), port.getNumber(), true);
            if(!signals.isEmpty()){
                signals.forEach(signal -> {
                    signal.setValue(calculatedValue);
                });
            } else {
                Signal signal = new Signal();
                signal.setValue(calculatedValue);
                signal.setFromElementNumber(this.neuron.getNumber());
                signal.setFromPortNumber(port.getNumber());
                signal.setToElementNumber(0);
                signal.setToPortNumber(0);
                component.addSignal(signal);
            }

        });
        return getPortsRunValues(this.neuron.getOutPorts(), true, calculatedValue);
    }

    private Set<RunValue> getPortsRunValues(Set<Port> ports, boolean isFrom, Double calculatedValue) {
        return ports.stream().map(port -> {
            RunValue runValue = new RunValue();
            Double value = null;
            if(!manager.getSignals(neuron.getNumber(), port.getNumber(), isFrom).isEmpty()){
                value = manager.getSignals(neuron.getNumber(), port.getNumber(), isFrom).iterator().next().getValue();
            } else {
                value = calculatedValue;
            }

            runValue.setValue(value);
            runValue.setElement(neuron);
            runValue.setPort(port);
            runValue.setTime(timer.getCurrentModelTime());
            return runValue;
        }).collect(Collectors.toSet());
    }

    public void setTimer(ModelTimer timer) {
        this.timer = timer;
    }
}
