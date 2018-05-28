package com.project.json.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.elements.Delay;
import com.project.model.component.Component;
import com.project.model.connetions.Port;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import org.springframework.stereotype.Repository;

@Repository
public class GsonComponentDeserializer {
	public Component desirealizeComponent(String jsonString) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Component.class, new ComponentDeserializer())
									.registerTypeAdapter(Signal.class, new SignalDeserializer())
									.registerTypeAdapter(Delay.class, new DelayDeserializer())
									.registerTypeAdapter(Neuron.class, new NeuronDeserializer())
									.registerTypeAdapter(Port.class, new PortDeserializer())
									.create();
		return gson.fromJson(jsonString, Component.class);
	}
}
