package com.project.run.executors;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import com.project.diagram.DiagramComponentConfigurator;
import com.project.elements.Delay;
import com.project.elements.generators.Generator;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.elements.timers.ModelTimer;
import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.component.Component;
import com.project.model.component.ComponentManager;
import com.project.model.component.ForeignComponent;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.exceptions.LogicException;
import com.project.model.exceptions.ModelTimerException;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;

public class ComponentRunner implements ElementRunner {

    private Component component;

    private ComponentManager manager = new ComponentManager();

    private ModelTimer timer;

    public ModelTimer getTimer() {
        return this.timer;
    }

    public ComponentRunner(Component component) {
        this.component = component;
        manager.setComponent(component);
    }

    private Element[][] getExecutionOrderedElements() {
        //TODO check!
        return manager.getExecutionOrderedElements();
    }

    @Override
    public Set<RunValue> execute() {
        Element[][] elements = getExecutionOrderedElements();
        Set<RunValue> runValues = new LinkedHashSet<>();
        if (this.timer == null) {
            this.timer = prepareTimer();
        }
        //TODO Check!
        //Set first layer elements values
        preExecute(elements[0]);
        while (timer.hasNext()) {
            for (Element[] elemLayer : elements) {
                for (Element elem : elemLayer) {
                    Set<RunValue> values = runElement(elem, timer);
                    System.out.println(values);
                    runValues.addAll(values);
                }
            }
        }
        return runValues;
    }

    private void preExecute(Element[] elements) {
        //Set values from generators in their signals
        Set<Element> generators = manager.getElements(ElementType.GENERATOR);
        for (Element generator : generators) {
            Set<Signal> signals = manager.getSignals(generator.getNumber(), 1, true);
            signals.forEach(signal -> {
                signal.setValue(((Generator) generator).getValue());
            });
        }
    }

    private ModelTimer prepareTimer() {
        ModelSimpleTimer timer = new ModelSimpleTimer();
        timer.start();
        return timer;
    }


    private Set<RunValue> runElement(Element elem, ModelTimer timer) {
        switch (elem.getType()) {
            case NEURON:
                NeuronRunner runner = new NeuronRunner((Neuron) elem);
                runner.setTimer(timer);
                runner.setComponent(component);
                return runner.execute();
            case GENERATOR:
                return getGeneratorRunValues((Generator) elem, timer);
            case COMPONENT:
                //FIXME !!!
                return getForeignComponentRunValues((ForeignComponent) elem, timer);
            case DELAY:
                return getDelayRunValues((Delay) elem, timer);
            case FOREIGNCOMPONENT:
                return getForeignComponentRunValues((ForeignComponent) elem, timer);
            default:
                throw new LogicException("Unknown element type " + elem.getType());
        }
    }

    private Set<RunValue> getForeignComponentRunValues(ForeignComponent foreignComponent, ModelTimer timer) {
        ForeignComponentRunnner runner = new ForeignComponentRunnner(foreignComponent);
        runner.setTimer(timer);
        runner.setComponent(component);
        return runner.execute();
    }

    private Set<RunValue> getDelayRunValues(Delay delay, ModelTimer timer) {
        delay.setTimer(timer);
        delay.setComponent(component);
        Double value = delay.getValue();
        Port outPort = delay.getOutPort();
        //set calculated values in out signals
        Set<Signal> signals = manager.getSignals(delay.getNumber(), outPort.getNumber(), true);
        if (signals != null && !signals.isEmpty()) {
            signals.forEach(signal -> {
                signal.setValue(value);
            });
        }


        RunValue runValue = new RunValue();
        runValue.setValue(value);
        runValue.setElement(delay);
        runValue.setTime(timer.getCurrentModelTime());
        runValue.setPort(outPort);
        LinkedHashSet<RunValue> runValues = new LinkedHashSet<>();
        runValues.add(runValue);
        return runValues;

    }

    public void setTimer(ModelTimer timer) {
        this.timer = timer;
    }

    private Set<RunValue> getGeneratorRunValues(Generator generator, ModelTimer timer) {
        generator.setTimer(timer);
        return generator.getPorts().stream().map(port -> {
            Double value = generator.getValue();

            //set calculated values in out signals
            if (port.getPosition().equals(PortPosition.OUT)) {
                Set<Signal> signals = manager.getSignals(generator.getNumber(), port.getNumber(), true);
                if (signals != null && !signals.isEmpty()) {
                    signals.forEach(signal -> {
                        signal.setValue(value);
                    });
                }
            }

            RunValue runValue = new RunValue();
            runValue.setValue(value);
            runValue.setElement(generator);
            runValue.setTime(timer.getCurrentModelTime());
            runValue.setPort(port);
            return runValue;
        }).collect(Collectors.toSet());
    }
}
