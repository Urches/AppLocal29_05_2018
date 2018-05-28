package com.project.data;

import com.google.gson.Gson;
import com.project.db.HibernateUtils;
import com.project.diagram.Diagram;
import com.project.diagram.DiagramComponentConfigurator;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.elements.timers.ModelTimer;
import com.project.elements.timers.TimerProperties;
import com.project.json.deserialize.GsonComponentDeserializer;
import com.project.model.component.Component;
import com.project.model.exceptions.LogicException;
import com.project.run.executors.ComponentRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class DAOComponentsImpl implements DAOComponents {

    private boolean isCacheValid = false;

    @Autowired
    private GsonComponentDeserializer deserializer;

    private List<Component> components;

    @Override
    public List<Component> getAllComponents() {
        List<Component> hibernateComponents;
        if (!isCacheValid) {
            try (Session session = HibernateUtils.getSession()) {
                session.beginTransaction();
                Query query = session.createQuery("from com.project.model.component.Component");
                hibernateComponents = (List<Component>) query.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                LogicException exception = new LogicException("Hibernate error!");
                exception.initCause(e);
                throw exception;
            }

            components = hibernateComponents.stream().map(component -> component = getComponent(component.getJsonScript()))
                    .collect(Collectors.toList());
            isCacheValid = true;
        }
        return components;
    }

    @Override
    public List<Component> getFiltredComponents(Predicate<Component> filter) {
        return getAllComponents().stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public Component getComponent(int number) {
        List<Component> filtredComponents;
        if (number > 0) {
            filtredComponents = getAllComponents().stream()
                    .filter(component -> component.getNumber() == number)
                    .collect(Collectors.toList());

            if (!filtredComponents.isEmpty()) {
                if (filtredComponents.size() == 1) {
                    return filtredComponents.get(0);
                } else {
                    throw new LogicException("More then one element was founded!");
                }
            } else {
                throw new LogicException("Component with number: " + number + " didn't find!");
            }
        } else {
            throw new LogicException("Search element number is " + number);
        }
    }

    @Override
    public void removeComponent(Component component) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.delete(component);
            session.getTransaction().commit();
        } catch (Exception e) {
            LogicException exception = new LogicException("Hibernate error!");
            exception.initCause(e);
            throw exception;
        }
        components.remove(component);
        isCacheValid = false;
    }

    @Override
    public void removeComponent(int number) {
        Component component = getComponent(number);
        removeComponent(component);
    }

    @Override
    public void updateComponent(int number, Component tempComponent) {
        tempComponent.setNumber(number);
        removeComponent(number);
        addComponent(tempComponent);
    }

    @Override
    public Component addComponent(Component component) {
        if(component.getNumber() != 0){
            try (Session session = HibernateUtils.getSession()) {
                session.beginTransaction();
                session.save(component);
                session.getTransaction().commit();
            } catch (Exception e) {
                LogicException exception = new LogicException("Hibernate error!");
                exception.initCause(e);
                throw exception;
            }
        } else {
            throw new LogicException("Number shouldn't be zero in this method!");
        }
        components.add(component);
        isCacheValid = false;
        return component;
    }

    @Override
    public Component addComponent(String jsonComponent) {
        Component component = getComponent(jsonComponent);
        Integer number = component.getNumber();
        if (number != 0) {
            updateComponent(number, component);
            return component;
        } else {
            number = generateComponentNumber();

            //little hack ;)
            final String regex = "[\\s]*[\\{]";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(jsonComponent);
            if (matcher.find()) {
                System.out.println("Found value: " + matcher.group(0));
                jsonComponent = jsonComponent.replaceFirst(regex, "{\"number\": " + number + " ,");
            }
            component.setNumber(number);
            //set updated json
            component = getComponent(jsonComponent);
            component = addComponent(component);

            return component;
        }
    }

    @Override
    public Integer generateComponentNumber() {
        Optional<Integer> maxNumber = getAllComponents().stream().map(Component::getNumber).reduce(Integer::max);
        return maxNumber.map(integer -> integer + 1).orElse(20000);
    }

    @Override
    public Diagram getDiagram(Component component, ModelTimer timer) {
        if(timer == null){
            timer = new ModelSimpleTimer();
        }

        DiagramComponentConfigurator configurator = new DiagramComponentConfigurator(component);
        ComponentRunner runner = new ComponentRunner(component);
        runner.setTimer(timer);
        configurator.setExecutor(runner);
        /**
         * Fix me smell!
         */
        timer.start();
        return configurator.getDiagram();
    }

    @Override
    public Component getComponent(String json) {
        return deserializer.desirealizeComponent(json);
    }

    @Override
    public TimerProperties getTimerProperties(String jsonProperties) {
        Gson gson = new Gson();
        TimerProperties properties = gson.fromJson(jsonProperties, TimerProperties.class);
        return properties;
    }
}
