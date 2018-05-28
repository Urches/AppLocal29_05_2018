package com.project.model.component;

import com.project.model.connetions.Port;

import java.util.Objects;

public class PortMapping {

    private Port outterPort;

    private Port innerPort;

    private int InnerElementNumber;

    public PortMapping(Port outterPort, Port innerPort, int innerElementNumber) {
        this.outterPort = outterPort;
        this.innerPort = innerPort;
        InnerElementNumber = innerElementNumber;
    }

    public Port getOutterPort() {
        return outterPort;
    }

    public void setOutterPort(Port outterPort) {
        this.outterPort = outterPort;
    }

    public Port getInnerPort() {
        return innerPort;
    }

    public void setInnerPort(Port innerPort) {
        this.innerPort = innerPort;
    }

    public int getInnerElementNumber() {
        return InnerElementNumber;
    }

    public void setInnerElementNumber(int innerElementNumber) {
        InnerElementNumber = innerElementNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortMapping that = (PortMapping) o;
        return InnerElementNumber == that.InnerElementNumber &&
                Objects.equals(outterPort, that.outterPort) &&
                Objects.equals(innerPort, that.innerPort);
    }

    @Override
    public int hashCode() {

        return Objects.hash(outterPort, innerPort, InnerElementNumber);
    }

    @Override
    public String toString() {
        return "PortMapping{" +
                "outterPort=" + outterPort +
                ", innerPort=" + innerPort +
                ", InnerElementNumber=" + InnerElementNumber +
                '}';
    }
}
