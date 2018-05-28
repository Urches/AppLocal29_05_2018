package com.project.json.deserialize;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.project.model.connetions.Port;
import com.project.model.exceptions.LogicException;
import com.project.model.functions.*;
import com.project.model.neurons.Neuron;

class NeuronDeserializer implements JsonDeserializer<Neuron> {

	@Override
	public Neuron deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		Neuron neuron = new Neuron();
		JsonObject jsonObject = json.getAsJsonObject();

		neuron.setNumber(jsonObject.get("number").getAsInt());
		//String activatedBlock = jsonObject.get("activatedBlock").getAsString().toUpperCase();

		//extract in class
		JsonElement activatedBlock = jsonObject.get("activatedBlock");
		ActivatedFunction function;
		if(activatedBlock.isJsonPrimitive()){
			function = FunctionController.getFunction(activatedBlock.getAsString());
		} else {
			JsonObject activatedBlockOnj = activatedBlock.getAsJsonObject();
			String afunction = activatedBlockOnj.get("afunction").getAsString();
			switch (afunction.toUpperCase()) {
				case "LINEAR":
					LinearFunction linearFunction = new LinearFunction();
					if (activatedBlockOnj.has("k")) {
						linearFunction.setK(activatedBlockOnj.get("k").getAsDouble());
					}
					function = linearFunction;
					break;
				case "LINEAR_BOUNDER":
					LinearBounderFunction linearBounderFunction = new LinearBounderFunction();
					if (activatedBlockOnj.has("bottom")) {
						linearBounderFunction.setBottom(activatedBlockOnj.get("bottom").getAsDouble());
					}
					if (activatedBlockOnj.has("top")) {
						linearBounderFunction.setTop(activatedBlockOnj.get("top").getAsDouble());
					}
					function = linearBounderFunction;
					break;
				case "SIGMOID":
					SigmoidFunction sigmoidFunction = new SigmoidFunction();
					if (activatedBlockOnj.has("t")) {
						sigmoidFunction.setT(activatedBlockOnj.get("t").getAsDouble());
					}
					function = sigmoidFunction;
					break;
				case "THRESHOLD":
					ThresholdFunction thresholdFunction = new ThresholdFunction();
					if (activatedBlockOnj.has("q")) {
						thresholdFunction.setQ(activatedBlockOnj.get("q").getAsDouble());
					}
					function = thresholdFunction;
					break;
				default:
					throw new LogicException("Unknown activated function!");
			}
		}

		//neuron.setActivatedFunction(FunctionController.getFunction(jsonObject.get("activatedBlock").getAsString()));
		neuron.setActivatedFunction(function);
		JsonArray jsonPorts = jsonObject.get("ports").getAsJsonArray();
		Set<Port> ports = new LinkedHashSet<>();

		for (JsonElement jsonElement : jsonPorts) {
			ports.add(context.deserialize(jsonElement, Port.class));
		}
		neuron.setPorts(ports);
		return neuron;
	}
}
