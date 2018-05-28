package com.project.json.deserialize;

import com.google.gson.*;
import com.project.elements.Delay;
import com.project.model.connetions.Port;
import com.project.model.functions.FunctionController;
import com.project.model.neurons.Neuron;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

public class DelayDeserializer implements JsonDeserializer<Delay> {
    @Override
    public Delay deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Delay delay = new Delay();
        JsonObject jsonObject = json.getAsJsonObject();

        delay.setNumber(jsonObject.get("number").getAsInt());
        delay.setDelayTime(jsonObject.get("delayTime").getAsInt());
        JsonArray jsonPorts = jsonObject.get("ports").getAsJsonArray();
        Set<Port> ports = new LinkedHashSet<>();
        for (JsonElement jsonElement : jsonPorts) {
            ports.add(context.deserialize(jsonElement, Port.class));
        }
        delay.setPorts(ports);
        return delay;
    }
}
