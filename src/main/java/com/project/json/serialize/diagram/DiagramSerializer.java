package com.project.json.serialize.diagram;

import com.google.gson.*;
import com.project.diagram.Diagram;
import com.project.diagram.DiagramLine;
import com.project.model.signals.Signal;

import java.lang.reflect.Type;

public class DiagramSerializer implements JsonSerializer<Diagram> {

    @Override
    public JsonElement serialize(Diagram diagram, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for(DiagramLine line : diagram.getLines()) {
            jsonArray.add(context.serialize(line, DiagramLine.class));
        }
        result.add("diagram", jsonArray);
        return result;
    }
}
