package com.project.model.component;

import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.signals.Signal;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ComponentManager {

    private Component component;

    public void setComponent(Component component) {
        this.component = component;
    }

    public Set<Port> getObservedPorts(){
        Set<Port> ports = new LinkedHashSet<>();
        this.component.getElements().forEach(element -> {
            element.getPorts().forEach(port -> {
                if(port.isObserved()){
                    ports.add(port);
                }
            });
        });
        return ports;
    }

//    public Set<Element> getGenerators(){
//        return getElements(ElementType.GENERATOR);
//    }

    public Set<Element> getElements(ElementType type) {
        return this.component.getElements().stream()
                .filter(element -> element.getType().equals(type))
                .collect(Collectors.toSet());
    }

    public Element[][] getExecutionOrderedElements() {
        Set<Set<Element>> elements = new LinkedHashSet<>();
        Set<Element> layer = getFirstLayer();
        while (!layer.isEmpty()){
            elements.add(layer);
            layer = getNextLayer(layer);
        }
        return elements.stream().map(el-> el.stream().toArray(Element[]::new)).toArray(Element[][]::new);
    }

    /**
     * Element stand in first layer if it haven't IN-ports or they are without signals
     * @return
     */
    public Set<Element> getFirstLayer() {
        Set<Element> elements = component.getElements().stream()
                .filter(element -> {
                    AtomicBoolean isEmpty = new AtomicBoolean(true);

                    element.getPorts().stream().forEach(port -> {
                        if(port.getPosition() == PortPosition.IN){
                            Set<Signal> signals = getSignals(element.getNumber(), port.getNumber(), false);
                            if(signals != null || !signals.isEmpty()){
                                isEmpty.set(false);
                            }
                        }
                    });
                    return isEmpty.get();
                }).collect(Collectors.toSet());

        return elements;
    }

//    public Set<Element> getElementsWithEmptyPort(PortPosition position){
//        component.getElements().stream().filter(element -> {
//            element.getPorts().stream().filter(port -> port.getPosition().equals(position))
//                    .
//        })
//    }

    /**
     * Element stand in last layer if it haven't OUT-ports or they are empty
     * @return
     */
    //public Set<Element> getLastLayer() {
//        return getBorderLayer(PortPosition.OUT);
//    }

//    private Set<Element> getBorderLayer(PortPosition pos) {
//        Set<Element> layer = new LinkedHashSet<>();
//        for (Element element : this.component.getElements()) {
//            long count = element.getPorts() != null ? element.getPorts().stream().filter(port -> {
//                if(pos.equals(port.getPosition())){
//                    return port.getSignal() != null;
//                } else {
//                    return false;
//                }
//            }).count() : 0;
//            System.out.println(count);
//            if (count == 0) {
//                layer.add(element);
//            }
//        }
//        return layer;
//    }

	public Set<Element> getNextLayer(Set<Element> prevLayer) {
		Set<Element> layer = new LinkedHashSet<>();
		for (Element element : prevLayer) {
           // System.out.println(element);
            element.getPorts().forEach(port -> {
                if(PortPosition.OUT.equals(port.getPosition())){
                    Set<Signal> filtredSignal = getSignals(element.getNumber(), port.getNumber(), true);
                    if(!filtredSignal.isEmpty()) {
                        filtredSignal.forEach(signal -> {
                            Set<Element> linkedElements = getLinkedElements(signal, false);
                            if(!linkedElements.isEmpty()){
                                layer.addAll(linkedElements);
                            }
                        });
                    }
                }
            });
		}
		return layer;
	}

    public Set<Signal> getSignals(int elementNumber, int portNumber, boolean isFrom) {
        return component.getSignals().stream().filter(signal -> {
            if(isFrom){
                return signal.getFromElementNumber() == elementNumber && signal.getFromPortNumber() == portNumber;
            } else {
//                System.out.println(signal);
                return signal.getToElementNumber() == elementNumber && signal.getToPortNumber() == portNumber;
            }
        }).collect(Collectors.toSet());
    }

    public Set<Element> getLinkedElements(Signal signal, boolean isFrom){
        return component.getElements(isFrom ? signal.getFromElementNumber() : signal.getToElementNumber());
    }

//	public Set<Element> getElementsBySignal(Signal signal){
//        Set<Element> filtredElements = new HashSet<>();
//        System.out.println(signal);
//        this.component.getElements().forEach(element -> {
//            Port port1 = element.getPorts().stream().filter(port ->
//                    signal.equals(port.getSignal())
//            ).findFirst().get();
//
//                filtredElements.add(element);
//        });
//        return filtredElements;
//    }



}
