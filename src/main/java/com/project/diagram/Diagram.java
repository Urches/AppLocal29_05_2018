package com.project.diagram;

import com.project.model.Element;
import com.project.run.executors.RunValue;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Diagram {

	private Set<DiagramLine> lines = new HashSet<>();

	public Diagram(Set<RunValue> runValues) {
		Map<Element, List<RunValue>> collect = runValues.stream().collect(Collectors.groupingBy(RunValue::getElement));
		this.lines = collect.entrySet().stream().map(elementListEntry -> {
			DiagramLine line = new DiagramLine();
			Element element = elementListEntry.getKey();
			line.setTitle(element.getType().name() + ": " + element.getNumber() +
					", PORT: " + elementListEntry.getValue().get(0).getPort().getNumber());
			elementListEntry.getValue().forEach(runValue -> {
				line.addValue(runValue.getTime(),runValue.getValue());
			});
			return line;
		}).collect(Collectors.toCollection (LinkedHashSet::new));
		//System.out.println(this.lines);
	}

	public Set<DiagramLine> getLines() {
		return lines;
	}
	
	public void add(DiagramLine line){
		lines.add(line);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Diagram diagram = (Diagram) o;
		return Objects.equals(lines, diagram.lines);
	}

	@Override
	public int hashCode() {

		return Objects.hash(lines);
	}

	@Override
	public String toString() {
		return "Diagram{" +
				"lines=" + lines +
				'}';
	}
}
