package com.project.json.serialize.diagram;

import com.google.gson.*;
import com.project.diagram.DiagramLine;

import java.lang.reflect.Type;
import java.util.Map;

public class DiagramLineSerializer implements JsonSerializer<DiagramLine> {
    @Override
    public JsonElement serialize(DiagramLine diagramLine, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", diagramLine.getTitle());
        jsonObject.addProperty("type", diagramLine.getType().name());
        Map<Long, Double> lineValues = diagramLine.getValues();
        JsonArray time = new JsonArray();
        JsonArray values = new JsonArray();
        System.out.println("lineValues: " + lineValues);
        lineValues.entrySet().forEach(entry -> {
            time.add(entry.getKey());
            values.add(entry.getValue());
        });
        jsonObject.add("time", time);
        jsonObject.add("values", values);
        return jsonObject;
    }
}
