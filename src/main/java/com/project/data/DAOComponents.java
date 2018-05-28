package com.project.data;

import com.project.diagram.Diagram;
import com.project.elements.timers.ModelTimer;
import com.project.elements.timers.TimerProperties;
import com.project.model.component.Component;

import java.util.List;
import java.util.function.Predicate;

public interface DAOComponents {
    List<Component> getAllComponents();

    List<Component> getFiltredComponents(Predicate<Component> filter);

    Component getComponent(int number);

    void removeComponent(Component component);

    void removeComponent(int number);

    void updateComponent(int number, Component tempComponent);

    Component addComponent(Component component);

    Component addComponent(String jsonComponent);

    Integer generateComponentNumber();

    Component getComponent(String json);

    TimerProperties getTimerProperties(String jsonProperties);

    Diagram getDiagram(Component component, ModelTimer timer);
}
