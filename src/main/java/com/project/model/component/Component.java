package com.project.model.component;

import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.signals.Signal;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Each component is characterized by its constituent elements, and the links
 * signals between them
 * 
 * @author barsu
 * @version 1.0
 */
@Entity
@Table(schema = "APP", name = "COMPONENT")
public class Component {

    @Column(name = "NUMBER")
	@Id
	private int number;

	@Transient
	private Set<Element> elements;
    @Transient
	private Set<Signal> signals = new LinkedHashSet<>();

    @Basic
    @Column(name = "JSON_SCRIPT")
	private String jsonScript;
    @Basic
	private String description;
    @Transient
	private boolean isNew = false;

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<Element> getElements() {
		return elements;
	}

	public void setElements(Set<Element> elements) {
		this.elements = elements;
	}

	public Element getElement(int number) {
		return elements.stream().filter(element -> element.getNumber() == number).findFirst().get();
	}

//	public ComponentEntry getStore() {
//		return store;
//	}
//
//	public void setStore(ComponentEntry store) {
//		this.store = store;
//	}

	public Set<Element> getElements(int number) {
		return elements.stream().filter(element -> element.getNumber() == number).collect(Collectors.toSet());
	}

	public void addSignal(Signal signal){
		signals.add(signal);
	}

	public String getJsonScript() {
		return jsonScript;
	}

	public void setJsonScript(String jsonScript) {
		this.jsonScript = jsonScript;
	}

	public void setSignals(Set<Signal> signals) {
		this.signals = signals;
	}

	public Set<Signal> getSignals() {
		return signals;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Component component = (Component) o;
		return number == component.number &&
				Objects.equals(elements, component.elements) &&
				Objects.equals(signals, component.signals);
	}

	@Override
	public int hashCode() {

		return Objects.hash(number,  elements, signals);
	}

	@Override
	public String toString() {
		return "Component{" +
				", number=" + number +
				", elements=" + elements +
				", signals=" + signals +
				", jsonScript='" + jsonScript + '\'' +
				'}';
	}
}

