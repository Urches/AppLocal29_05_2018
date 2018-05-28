package com.project.json.deserialize;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.project.model.connetions.ConnectionType;
import com.project.model.signals.Signal;


class SignalDeserializer implements JsonDeserializer<Signal> {

	public Signal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObj = json.getAsJsonObject();

		Signal signal = new Signal();
		if(jsonObj.has("type")){
			signal.setType(ConnectionType.valueOf(jsonObj.get("type").getAsString().toUpperCase()));
		}
		signal.setFromElementNumber(jsonObj.get("fromElement").getAsInt());
		signal.setFromPortNumber(jsonObj.get("fromPort").getAsInt());
		signal.setToElementNumber(jsonObj.get("toElement").getAsInt());
		signal.setToPortNumber(jsonObj.get("toPort").getAsInt());
		if(jsonObj.has("value")){
			signal.setValue(jsonObj.get("value").getAsDouble());
		}
		return signal;
	}
}
