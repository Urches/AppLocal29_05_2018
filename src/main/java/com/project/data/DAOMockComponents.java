package com.project.data;

import com.project.diagram.Diagram;
import com.project.diagram.DiagramComponentConfigurator;
import com.project.elements.timers.ModelTimer;
import com.project.elements.timers.TimerProperties;
import com.project.json.deserialize.GsonComponentDeserializer;
import com.project.json.serialize.GsonDiagramSerializer;
import com.project.model.component.Component;
import com.project.model.component.ComponentEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DAOMockComponents implements DAOComponents {

    private List<Component> components = new ArrayList<>();

    @Autowired
    private GsonComponentDeserializer deserializer;

    @Override
    public List<Component> getAllComponents() {
        return components;
    }

    @Override
    public List<Component> getFiltredComponents(Predicate<Component> filter) {
        return components.stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public Component getComponent(int number) {
        return getFiltredComponents(component -> component.getNumber() == number).get(0);
    }

    @Override
    public void removeComponent(Component component) {
        components.remove(component);
    }

    @Override
    public void removeComponent(int number) {
        components.remove(getComponent(number));
    }

    @Override
    public void updateComponent(int number, Component tempComponent) {
        removeComponent(number);
        tempComponent.setNumber(number);
        components.add(tempComponent);
    }

    @Override
    public Component addComponent(Component component) {
        if(component.isNew()){
            component.setNumber(generateComponentNumber());
        }
        components.add(component);
        return component;
    }

    @Override
    public Component addComponent(String jsonComponent) {
        Integer number = generateComponentNumber();

        //little hack ;)
        final String regex = "[\\s]*[\\{]";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(jsonComponent);
        if (matcher.find()) {
            System.out.println("Found value: " + matcher.group(0));
            jsonComponent = jsonComponent.replaceFirst(regex,  "{\"number\": " + number + " ,");
            System.out.println(jsonComponent);
        }

        Component component = new Component();
        component.setJsonScript(jsonComponent);
        component.setNumber(number);
        components.add(component);
        return component;
    }

    @Override
    public Integer generateComponentNumber() {
        Optional<Integer> maxNumber = getAllComponents().stream().map(Component::getNumber).reduce((integer, integer2) -> integer > integer2 ? integer : integer2);
        return maxNumber.map(integer -> integer + 1).orElse(20000);
    }


    public Diagram getDiagram(Component component) {
        DiagramComponentConfigurator configurator = new DiagramComponentConfigurator(component);
        return configurator.getDiagram();
    }

    @Override
    public Component getComponent(String json) {
        Component component = deserializer.desirealizeComponent(json);
        component.setJsonScript(json);
        return component;
    }

    @Override
    public TimerProperties getTimerProperties(String jsonProperties) {
        return null;
    }

    @Override
    public Diagram getDiagram(Component component, ModelTimer timer) {
        return null;
    }
}
