package com.project.services;

import com.project.diagram.Diagram;
import com.project.diagram.DiagramComponentConfigurator;
import com.project.json.deserialize.GsonComponentDeserializer;
import com.project.model.component.Component;
import com.project.run.configs.ComponentRunConfig;

public class Service {
	
	public Diagram getComponentDiagram(Component component){
		DiagramComponentConfigurator configurator = new DiagramComponentConfigurator(component);
		return configurator.getDiagram();
	}
}
