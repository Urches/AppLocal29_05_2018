package com.project.json.deserialize;

import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.project.data.DAOComponents;
import com.project.data.DAOComponentsImpl;
import com.project.elements.Delay;
import com.project.elements.generators.*;
import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.component.Component;
import com.project.model.component.ForeignComponent;
import com.project.model.component.PortMapping;
import com.project.model.connetions.Port;
import com.project.model.exceptions.LogicException;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import org.springframework.beans.factory.annotation.Autowired;

class ComponentDeserializer implements JsonDeserializer<Component> {

    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (json.toString() != null && (!"".equals(json.toString()))) {

            JsonObject jsonObject = json.getAsJsonObject();
            Component component = new Component();
            component.setJsonScript(jsonObject.toString());

            if (jsonObject.has("number")) {
                component.setNumber(jsonObject.get("number").getAsInt());
            }

            if (jsonObject.has("description")) {
                component.setDescription(jsonObject.get("description").getAsString());
            }

            if (jsonObject.has("isNew")) {
                component.setNew(jsonObject.get("isNew").getAsBoolean());
            }

            //Elements deserialize
            if (jsonObject.has("elements")) {
                JsonArray jsonElements = jsonObject.get("elements").getAsJsonArray();
                Set<Element> elements = new LinkedHashSet<>();

                Set<Neuron> neurons = new LinkedHashSet<>();
                Set<Generator> generators = new LinkedHashSet<>();
                Set<ForeignComponent> foreignComponents = new LinkedHashSet<>();
                Set<Delay> delays = new LinkedHashSet<>();

                for (JsonElement jsonElement : jsonElements) {
                    JsonObject jObject = jsonElement.getAsJsonObject();
                    ElementType type = ElementType.valueOf(jObject.get("type").getAsString().toUpperCase());

                    if (ElementType.NEURON.equals(type)) {
                        neurons.add(context.deserialize(jObject, Neuron.class));
                    } else if (ElementType.FOREIGNCOMPONENT.equals(type)) {
                        foreignComponents.add(foreignComponentDeserialize(context, jObject));
                    } else if (ElementType.GENERATOR.equals(type)) {
                        generators.add(generatorDeserialize(context, jObject));
                    } else if (ElementType.DELAY.equals(type)) {
                        delays.add(context.deserialize(jObject, Delay.class));
                    } else {
                        System.err.println("Type not found: " + type);
                    }
                }
                elements.addAll(neurons);
                elements.addAll(delays);
                elements.addAll(generators);
                //TODO check!
                elements.addAll(foreignComponents);
                component.setElements(elements);
            }

            if (jsonObject.has("signals")) {
                //Signals deserialize
                JsonArray jsonSignals = jsonObject.get("signals").getAsJsonArray();
                //Set<Signal> signals = new LinkedHashSet<>();
                for (JsonElement jsonSignal : jsonSignals) {
                    component.addSignal(signalDeserialize(context, jsonSignal.getAsJsonObject()));
                }
            }
            //System.out.println(generators);
            //System.out.println(component);
            return component;
        } else {
            throw new LogicException("Script is empty!");
        }
    }

    private Signal signalDeserialize(JsonDeserializationContext context, JsonObject jObject) {
        return context.deserialize(jObject, Signal.class);
    }

    private Generator generatorDeserialize(JsonDeserializationContext context, JsonObject jObject) {
        Generator generator = null;
        Set<Port> ports = new HashSet<>();
        if( jObject.has("ports")){
            JsonArray jsonPorts = jObject.get("ports").getAsJsonArray();
            for (JsonElement jsonElement : jsonPorts) {
                ports.add(context.deserialize(jsonElement, Port.class));
            }
        }

        JsonObject properties = jObject.get("properties").getAsJsonObject();
        if(jObject.has("port")){
            Port port = context.deserialize(jObject.get("port"), Port.class);
            ports.add(port);
        }

        GeneratorsTypes type = GeneratorsTypes.valueOf(properties.get("type").getAsString().toUpperCase());
        int number = jObject.get("number").getAsInt();

        if (GeneratorsTypes.STATIC.equals(type)) {
            StaticGenerator staticGenerator = new StaticGenerator();


            staticGenerator.setValue(properties.get("value").getAsDouble());
            generator = staticGenerator;
        } else if (GeneratorsTypes.ASYMMETRICAL.equals(type)) {
            //TODO Check!
            AsymmetricalGenerator asymmetricalGenerator = new AsymmetricalGenerator();
            if (properties.has("values")) {
                JsonArray values = properties.get("values").getAsJsonArray();
                for (JsonElement value : values) {
                    JsonObject jsonObject = value.getAsJsonObject();
                    int fromTime = jsonObject.get("fromTime").getAsInt();
                    int toTime = jsonObject.get("toTime").getAsInt();
                    double aDouble = jsonObject.get("value").getAsDouble();
                    asymmetricalGenerator.addValue(fromTime, toTime, aDouble);
                }
            }
            generator = asymmetricalGenerator;
        } else if (GeneratorsTypes.FREQUENCY.equals(type)) {
            //TODO Check!
            FrequencyGenerator frequencyGenerator = new FrequencyGenerator();
            frequencyGenerator.setInterval(properties.get("interval").getAsInt());
            System.err.println("Not realized: " + type);
            generator = frequencyGenerator;
        } else {
            System.err.println("Type not found: " + type);
        }

        generator.setNumber(number);
        generator.setPorts(ports);
        return generator;
    }

    private ForeignComponent foreignComponentDeserialize(JsonDeserializationContext context, JsonObject jObject) {
        ForeignComponent foreignComponent = new ForeignComponent();
        foreignComponent.setNumber(jObject.get("number").getAsInt());
        foreignComponent.setDescription(jObject.get("description").getAsString());
        JsonObject componentObject = jObject.get("component").getAsJsonObject();

        JsonArray jsonPorts = jObject.get("ports").getAsJsonArray();

        Set<PortMapping> portsMapping = new LinkedHashSet<>();

        for (JsonElement jsonElement : jsonPorts) {
            Port outterPort = context.deserialize(jsonElement, Port.class);
            if(jsonElement.getAsJsonObject().has("innerPort")){
                JsonElement innerPortJson = jsonElement.getAsJsonObject().get("innerPort");
                int number = innerPortJson.getAsJsonObject().get("element").getAsInt();
                Port innerPort = context.deserialize(innerPortJson, Port.class);
                portsMapping.add(new PortMapping(outterPort, innerPort, number));
            }
        }
        foreignComponent.setPortsMapping(portsMapping);

        System.out.println(componentObject);
        Component innerComponent = context.deserialize(componentObject, Component.class);
        int number = innerComponent.getNumber();
        if(innerComponent != null && number != 0){
//            Component component = new DAOComponentsImpl().getComponent(number);
//            if(component.equals(innerComponent)){
//                System.out.println("Component equals!");
//                foreignComponent.setComponent(component);
//            } else {
                System.out.println("Component aren't equals! Will be used received one!");
                foreignComponent.setComponent(innerComponent);
           // }

        } else {
            throw new LogicException("Fail to parse foreign component!");
        }
        return foreignComponent;
    }
}
