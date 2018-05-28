package com.project.run.executors;

import com.project.model.Element;
import com.project.model.connetions.Port;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class RunValue implements Comparable<RunValue>{
    private long time;
    private Double value;
    private Element element;
    private Port port;

    public long getTime() {
        return time;
    }

    public Double getValue() {
        return value;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunValue runValue = (RunValue) o;
        return time == runValue.time &&
                Objects.equals(value, runValue.value) &&
                Objects.equals(element, runValue.element) &&
                Objects.equals(port, runValue.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, value, element, port);
    }

    @Override
    public String toString() {
        return "RunValue{" +
                "time=" + time +
                ", value=" + value +
                ", element  with number=" + element.getNumber() +
                ", port with number=" + port.getNumber() +
                '}';
    }


    @Override
    public int compareTo(RunValue o) {
        Comparator<RunValue> comparator = (o1, o2) -> {
            int timeDiff = (int) (o2.getTime() - o1.getTime());
            if (timeDiff == 0) {
                int elementDiff = o2.getElement().getNumber() - o1.getElement().getNumber();
                if(elementDiff == 0){
                    return o2.getPort().getNumber() - o1.getPort().getNumber();
                } else {
                    return elementDiff;
                }
            } else {
                return timeDiff;
            }
        };
        return comparator.compare(o, this);
    }
}