package com.project.elements;

import com.project.elements.timers.ModelTimer;
import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.component.Component;
import com.project.model.component.ComponentManager;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.signals.Signal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Delay extends Element {

    public static final Double DEFAULT_VALUE = 0.0;

    private ModelTimer timer;

    private Queue<Double> delayedValues = new LinkedList<>();

    private long delayTime;

    private long residueDelay;

    private ComponentManager manager = new ComponentManager();

    public Delay(){
        this.type = ElementType.DELAY;
    }

    public ModelTimer getTimer() {
        return timer;
    }

    public void setTimer(ModelTimer timer) {
        this.timer = timer;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
        this.residueDelay = delayTime;
    }

    public Port getOutPort(){
        return this.getPorts().stream().filter(port -> PortPosition.OUT.equals(port.getPosition()))
                .findAny().get();
    }

    public Port getInPort(){
        return getPorts().stream().filter(port -> port.getPosition().equals(PortPosition.IN))
                .findAny().get();
    }

    private void addValue(){
        Port inPort = getInPort();
        Set<Signal> signals = manager.getSignals(this.getNumber(), inPort.getNumber(), false);
        if(signals != null && !signals.isEmpty()){
            delayedValues.add(signals.iterator().next().getValue());
        }
    }

    public Double getValue() {
        addValue();
        if(residueDelay < 0){
            return delayedValues.poll();
        } else {
            residueDelay = residueDelay - timer.getStep();
            return DEFAULT_VALUE;
        }
    }

    public void setComponent(Component component) {
        this.manager.setComponent(component);
    }
}
