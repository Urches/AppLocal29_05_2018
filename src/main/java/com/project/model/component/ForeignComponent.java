package com.project.model.component;

import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ForeignComponent extends Element {

    private Component component;

    private Set<PortMapping> portsMapping;
    {
        this.type = ElementType.FOREIGNCOMPONENT;
    }

    public Set<PortMapping> getPortsMapping() {
        return portsMapping;
    }

    public void setPortsMapping(Set<PortMapping> portsMapping) {
        this.portsMapping = portsMapping;

        //Be carefully!
        this.ports = portsMapping.stream().map(PortMapping::getOutterPort)
                .collect(Collectors.toSet());
    }

    public Component getComponent() {
        return component;
    }


    public Set<Port> getInPorts(){
        return this.ports.stream()
                .filter(port -> PortPosition.IN.equals(port.getPosition()))
                .collect(Collectors.toSet());
    }

    public Set<Port> getOutPorts(){
        return this.ports.stream()
                .filter(port -> PortPosition.OUT.equals(port.getPosition()))
                .collect(Collectors.toSet());
    }


    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ForeignComponent that = (ForeignComponent) o;
        return Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), component);
    }

    @Override
    public String toString() {
        return "ForeignComponent{" +
                "component=" + component +
                ", number=" + number +
                ", description='" + description + '\'' +
                '}';
    }
}
