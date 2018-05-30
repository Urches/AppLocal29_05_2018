package com.project.model.neurons;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.functions.ActivatedFunction;

public class Neuron extends Element {

	private int number;

	private String name;

	private ActivatedFunction activatedFunction;

	public Neuron() {
		this.type = ElementType.NEURON;
	}

	@Override
	public void setNumber(int number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActivatedFunction(ActivatedFunction activatedFunction) {
		this.activatedFunction = activatedFunction;
	}

	public Neuron(int number, ActivatedFunction activatedFunction) {
		this.number = number;
		this.activatedFunction = activatedFunction;
	}
	
	public int getNumber(){
		return number;
	}
	
	public Set<Port> getInPorts() {
		return ports.stream().filter(p -> p.getPosition() == PortPosition.IN)
				.collect(Collectors.toSet());
	}
	
	public Set<Port> getOutPorts() {
		return ports.stream().filter(p -> p.getPosition() == PortPosition.OUT)
				.collect(Collectors.toSet());
	}

	public void addPort(Port port){
		ports.add(port);
	}

	public String getName() {
		return name;
	}

	public ActivatedFunction getActivatedFunction() {
		return activatedFunction;
	}

	@Override
	public String toString() {
		return "Neuron{" +
				"number=" + number +
				", name='" + name + '\'' +
				", ports=" + ports +
				", activatedFunction=" + activatedFunction +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Neuron neuron = (Neuron) o;
		return number == neuron.number &&
				Objects.equals(name, neuron.name) &&
				Objects.equals(ports, neuron.ports) &&
				Objects.equals(activatedFunction, neuron.activatedFunction);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), number, name, ports, activatedFunction);
	}
}
