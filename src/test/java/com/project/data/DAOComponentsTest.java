package com.project.data;

import com.project.model.component.Component;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class DAOComponentsTest {

    private DAOComponentsImpl dao;
    Component component1 = new Component();
    Component component2 = new Component();
    Component component3 = new Component();

    @Before
    public void setUp() throws Exception {
        dao = new DAOComponentsImpl();
        component1.setNumber(20000);
        component1.setJsonScript("{\n" +
                "   \"elements\": [\n" +
                "      {\n" +
                "         \"ports\": [\n" +
                "            {\n" +
                "               \"position\": \"in\",\n" +
                "               \"type\": \"digital\",\n" +
                "               \"number\": 1\n" +
                "            },\n" +
                "            {\n" +
                "               \"position\": \"in\",\n" +
                "               \"type\": \"logic\",\n" +
                "               \"number\": 2\n" +
                "            },\n" +
                "            {\n" +
                "               \"position\": \"out\",\n" +
                "               \"type\": \"digital\",\n" +
                "               \"number\": 3,\n" +
                "               \"observed\": true\n" +
                "            }\n" +
                "         ],\n" +
                "         \"type\": \"neuron\",\n" +
                "         \"number\": 3,\n" +
                "         \"activatedBlock\": \"sigmoid\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"number\": 6,\n" +
                "         \"type\": \"generator\",\n" +
                "         \"properties\": {\n" +
                "            \"type\": \"static\",\n" +
                "            \"value\": 0\n" +
                "         },\n" +
                "         \"port\": {\n" +
                "            \"position\": \"out\",\n" +
                "            \"number\": 1,\n" +
                "            \"type\": \"digital\"\n" +
                "         }\n" +
                "      }\n" +
                "   ],\n" +
                "   \"signals\": [\n" +
                "   ],\n" +
                "   \"description\": \"Тестовый компонент 1\",\n" +
                "   \"number\": 20000\n" +
                "}");
        component2.setNumber(20002);
        component2.setJsonScript("{\n" +
                "   \"elements\": [\n" +
                "      {\n" +
                "         \"ports\": [\n" +
                "            {\n" +
                "               \"position\": \"in\",\n" +
                "               \"type\": \"digital\",\n" +
                "               \"number\": 1\n" +
                "            },\n" +
                "            {\n" +
                "               \"position\": \"in\",\n" +
                "               \"type\": \"logic\",\n" +
                "               \"number\": 2\n" +
                "            },\n" +
                "            {\n" +
                "               \"position\": \"out\",\n" +
                "               \"type\": \"digital\",\n" +
                "               \"number\": 3,\n" +
                "               \"observed\": true\n" +
                "            }\n" +
                "         ],\n" +
                "         \"type\": \"neuron\",\n" +
                "         \"number\": 3,\n" +
                "         \"activatedBlock\": \"sigmoid\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"number\": 6,\n" +
                "         \"type\": \"generator\",\n" +
                "         \"properties\": {\n" +
                "            \"type\": \"static\",\n" +
                "            \"value\": 0\n" +
                "         },\n" +
                "         \"port\": {\n" +
                "            \"position\": \"out\",\n" +
                "            \"number\": 1,\n" +
                "            \"type\": \"digital\"\n" +
                "         }\n" +
                "      }\n" +
                "   ],\n" +
                "   \"signals\": [\n" +
                "   ],\n" +
                "   \"description\": \"Тестовый компонент 2\",\n" +
                "   \"number\": 20002\n" +
                "}");
        component3.setNumber(20003);
        component3.setJsonScript("{\n" +
                "   \"elements\": [\n" +
                "      {\n" +
                "         \"ports\": [\n" +
                "            {\n" +
                "               \"position\": \"in\",\n" +
                "               \"type\": \"digital\",\n" +
                "               \"number\": 1\n" +
                "            },\n" +
                "            {\n" +
                "               \"position\": \"in\",\n" +
                "               \"type\": \"logic\",\n" +
                "               \"number\": 2\n" +
                "            },\n" +
                "            {\n" +
                "               \"position\": \"out\",\n" +
                "               \"type\": \"digital\",\n" +
                "               \"number\": 3,\n" +
                "               \"observed\": true\n" +
                "            }\n" +
                "         ],\n" +
                "         \"type\": \"neuron\",\n" +
                "         \"number\": 3,\n" +
                "         \"activatedBlock\": \"sigmoid\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"number\": 6,\n" +
                "         \"type\": \"generator\",\n" +
                "         \"properties\": {\n" +
                "            \"type\": \"static\",\n" +
                "            \"value\": 0\n" +
                "         },\n" +
                "         \"port\": {\n" +
                "            \"position\": \"out\",\n" +
                "            \"number\": 1,\n" +
                "            \"type\": \"digital\"\n" +
                "         }\n" +
                "      }\n" +
                "   ],\n" +
                "   \"signals\": [\n" +
                "   ],\n" +
                "   \"description\": \"Тестовый компонент 3\",\n" +
                "   \"number\": 20003\n" +
                "}");
        try {
            dao.addComponent(component1);
            dao.addComponent(component2);
            dao.addComponent(component3);
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }

    @After
    public void after(){
        try {
            dao.removeComponent(component1);
            dao.removeComponent(component2);
            dao.removeComponent(component3);
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }

    @Test
    public void getAllComponents() {
        List<Component> components = dao.getAllComponents();
        List<Integer> numbers = components.stream().map(Component::getNumber).collect(Collectors.toList());

        Assert.assertTrue(numbers.contains(component1.getNumber()));
        Assert.assertTrue(numbers.contains(component2.getNumber()));
        Assert.assertTrue(numbers.contains(component3.getNumber()));
    }

    @Test
    public void getComponent() {
        Component component = dao.getComponent(component1.getNumber());
        Assert.assertEquals(component1.getNumber(), component.getNumber());
    }

    @Test
    public void updateComponent() {
//        dao.updateComponent(20003, component2);
//        Assert.assertEquals(20003, component2.getNumber());
//        Component component = dao.getComponent(component2.getNumber());
//        Assert.assertEquals(component2.getNumber(), component.getNumber());
    }
}