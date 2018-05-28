package com.project.json.deserialize;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.project.model.connetions.ConnectionType;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;

class PortDeserializer implements JsonDeserializer<Port>  {

	@Override
	public Port deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Port port = new Port();
		JsonObject jsonObject = json.getAsJsonObject();
		port.setNumber(jsonObject.get("number").getAsInt());
		port.setPosition(PortPosition.valueOf(jsonObject.get("position").getAsString().toUpperCase()));
		port.setType(ConnectionType.valueOf(jsonObject.get("type").getAsString().toUpperCase()));
		if(jsonObject.has("observed")){
			port.setObserved(jsonObject.get("observed").getAsBoolean());
		} else {
			port.setObserved(false);
		}

		return port;
	}

}
