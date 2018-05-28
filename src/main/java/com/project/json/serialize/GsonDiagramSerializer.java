package com.project.json.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.diagram.Diagram;
import com.project.diagram.DiagramLine;
import com.project.json.serialize.diagram.DiagramLineSerializer;
import com.project.json.serialize.diagram.DiagramSerializer;

public class GsonDiagramSerializer {
    public String serializeComponents(Diagram diagram) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Diagram.class, new DiagramSerializer())
                .registerTypeAdapter(DiagramLine.class, new DiagramLineSerializer())
                .create();
        return gson.toJson(diagram);
    }
}
