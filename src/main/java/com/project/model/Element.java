package com.project.model;

import com.project.model.connetions.Port;

import java.util.Objects;
import java.util.Set;

public class Element {

    protected int number;
    protected String description;
    protected Set<Port> ports;
    protected ElementType type;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public Set<Port> getPorts() {
        return ports;
    }

    public void setPorts(Set<Port> ports) {
        this.ports = ports;
    }

    public Port getPort(int number){
        return this.ports.stream().filter(p -> p.getNumber() == number).findFirst().get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return number == element.number &&
                Objects.equals(description, element.description) &&
                type == element.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, description, type);
    }

    @Override
    public String toString() {
        return "Element{" +
                "number=" + number +
                ", description='" + description + '\'' +
                ", ports=" + ports +
                ", type=" + type +
                '}';
    }
}
