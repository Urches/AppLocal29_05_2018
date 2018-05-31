package com.project.diagram;

import java.util.*;
import java.util.stream.Collectors;

import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.component.ComponentManager;
import com.project.model.connetions.Port;
import com.project.run.executors.ComponentRunner;
import com.project.run.executors.RunValue;

public class DiagramComponentConfigurator {

    private ComponentRunner executor;

    private Component component;

    private ComponentManager manager = new ComponentManager();
    private Set<RunValue> values;

    public DiagramComponentConfigurator(Component component) {
        this.component = component;
        manager.setComponent(component);
    }

    public Diagram getDiagram() {

        if(executor == null){
            executor = new ComponentRunner(component);
        }
        Set<RunValue> runValues = executor.execute();
        Set<RunValue> observedValues = getObservedValues(runValues);
        Diagram diagram = new Diagram(observedValues);
        return diagram;
    }

    private Set<RunValue> getObservedValues(Set<RunValue> values){
        Set<RunValue> filtred = new TreeSet<>();
        for (Element element : this.component.getElements()) {
            element.getPorts().stream()
                    .filter(Port::isObserved)
                    .map(port -> getObservedValues(values, port))
                    .reduce(this::runValuesConcat)
                    .ifPresent(filtred::addAll);

        }
        return filtred;
    }

    private Set<RunValue> getObservedValues(Set<RunValue> values, Port observedPort){
        return values.stream()
                .filter(runValue ->
                    runValue.getElement().getNumber() == observedPort.getElement().getNumber() &&
                    runValue.getPort().getNumber() == observedPort.getNumber())
                .collect(Collectors.toSet());
    }

    private Set<RunValue> runValuesConcat(Set<RunValue> runValues, Set<RunValue> runValues2){
        runValues.addAll(runValues2);
        return  runValues;
    }

    public void setExecutor(ComponentRunner executor) {
        this.executor = executor;
    }
}
