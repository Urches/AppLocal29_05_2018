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

        //System.out.println("observedValues: " + observedValues);

        Diagram diagram = new Diagram(observedValues);
        return diagram;
    }

//    private Set<PortListener> prepareListeners(){
//         return manager.getObservedPorts().stream()
//                .map(port -> new PortListener(port.getElement(), port))
//                .collect(Collectors.toSet());
//    }

    private Set<RunValue> getObservedValues(Set<RunValue> values){
        Set<RunValue> filtred = new TreeSet<>();
        this.component.getElements().forEach(element -> {
            element.getPorts().forEach(port -> {
                if(port.isObserved()){
                    filtred.addAll(values.stream().filter(runValue -> runValue.getElement().getNumber() == element.getNumber() &&
                            runValue.getPort().getNumber() == port.getNumber())
                            .collect(Collectors.toSet()));
                }
            });
        });
        return filtred;
    }

    public void setExecutor(ComponentRunner executor) {
        this.executor = executor;
    }

//    public class PortListener {
//
//        private Port port = null;
//        private Element element = null;
//        private Set<RunValue> values =
//                new TreeSet<>();
//
//
//        PortListener(Element element, Port port) {
//            this.port = port;
//            this.element = element;
//        }
//
//        public Port getPort() {
//            return port;
//        }
//
//        public void setPort(Port port) {
//            this.port = port;
//        }
//
//        public Element getElement() {
//            return element;
//        }
//
//        public void setElement(Element element) {
//            this.element = element;
//        }
//
//        public Set<RunValue> getValues() {
//            return values;
//        }
//
//        public void setValues(Set<RunValue> values) {
//            this.values = values;
//        }
//
//        public void notifyValue(RunValue value) {
//            //TODO !!!
//            if (Objects.equals(this.element, value.getElement()) &&
//                    Objects.equals(this.port, value.getPort())) {
//                values.add(value);
//            }
//        }
//
//
//    }
}
