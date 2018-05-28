package com.project.run.executors;

import com.project.elements.timers.ModelSimpleTimer;
import com.project.elements.timers.ModelTimer;
import com.project.model.component.Component;
import com.project.model.component.ComponentManager;
import com.project.model.component.ForeignComponent;
import com.project.model.signals.Signal;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ForeignComponentRunnner implements ElementRunner {

    private ForeignComponent foreignComponent;
    private ModelTimer timer;
    private Component component;

    private ComponentManager manager = new ComponentManager();

    public ForeignComponentRunnner(ForeignComponent foreignComponent) {
        this.foreignComponent = foreignComponent;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public ModelTimer getTimer() {
        return timer;
    }

    public void setTimer(ModelTimer timer) {
        this.timer = timer;
    }

    @Override
    public Set<RunValue> execute() {
        preExecute();
        Set<RunValue> runValues = new LinkedHashSet<>();

        ComponentRunner runner = new ComponentRunner(foreignComponent.getComponent());
        //Smell only one step!
        ModelSimpleTimer timer = new ModelSimpleTimer();
        timer.setLastTime(this.timer.getLastTime());
        timer.setFinishTime(this.timer.getLastTime() + this.timer.getStep());
        timer.setStep(this.timer.getStep());
        runner.setTimer(this.timer);

        Set<RunValue> values = runner.execute();
        System.out.println("inner component values!");
        System.out.println(values);
        //smell!
        runValues.addAll(new LinkedHashSet<>());
        postExecute();
        return runValues;
    }

    /**
     * TODO Check it !
     * add temp signals from inner connected signals
     */
    private void preExecute() {
        manager.setComponent(component);
        this.foreignComponent.getInPorts().forEach(port -> {
            Set<Signal> signals = manager.getSignals(foreignComponent.getNumber(), port.getNumber(), false);
            if (signals != null && !signals.isEmpty()) {
                if (signals.size() > 1) {
                    System.out.println("something wrong!");
                }
                //add new temp signals
                Signal foreignSignal = signals.iterator().next();
                Set<Signal> tempInnerSignals = foreignComponent.getPortsMapping().stream()
                        .filter(portMapping -> portMapping.getOutterPort().getNumber() == foreignSignal.getToPortNumber())
                        .map(portMapping -> {
                            Signal signal = new Signal();
                            signal.setFromElementNumber(0);
                            signal.setFromPortNumber(0);
                            signal.setToElementNumber(portMapping.getInnerElementNumber());
                            signal.setToPortNumber(portMapping.getInnerPort().getNumber());
                            signal.setValue(foreignSignal.getValue());
                            return signal;
                        }).collect(Collectors.toSet());
                foreignComponent.getComponent().getSignals().addAll(tempInnerSignals);
            }
        });
    }

    /**
     * Set values of outer signals
     * Delete temp signals
     */
    private void postExecute() {
        //Set values of outer signals
        this.foreignComponent.getPortsMapping().forEach(portMapping -> {
            manager.setComponent(this.foreignComponent.getComponent());
            Set<Signal> innerSignals = manager.getSignals(portMapping.getInnerElementNumber(), portMapping.getInnerPort().getNumber(), true);

            manager.setComponent(this.component);
            Set<Signal> outerSignals = manager.getSignals(this.foreignComponent.getNumber(), portMapping.getOutterPort().getNumber(), true);

            List<Signal> innerList = innerSignals.stream().collect(Collectors.toList());

            int i = 0;
            for (Signal outerSignal : outerSignals) {
                outerSignal.setValue(innerList.get(i).getValue());
                i++;
            }
        });
        //Delete temp signals
        foreignComponent.getComponent().getSignals()
                .removeIf(signal -> signal.getFromElementNumber() == 0 && signal.getFromPortNumber() == 0);
    }
}
