package com.project.diagram;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.project.model.connetions.ConnectionType;

public class DiagramLine {

    private ConnectionType type;

    private String title;

    private Map<Long, Double> values = new LinkedHashMap<Long, Double>();

    public DiagramLine() {
	this(ConnectionType.DIGITAL);
    }

    public DiagramLine(ConnectionType type) {
	this.type = type;
    }

    public DiagramLine addValue(Long key, Double value) {
	    values.put(key, value);
	    return this;
    }

    public ConnectionType getType() {
	return type;
    }

    public Map<Long, Double> getValues() {
	    return values;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagramLine that = (DiagramLine) o;
        return type == that.type &&
                Objects.equals(title, that.title) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, title, values);
    }

    @Override
    public String toString() {
        return "DiagramLine{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", values=" + values +
                '}' + '\n';
    }
}
