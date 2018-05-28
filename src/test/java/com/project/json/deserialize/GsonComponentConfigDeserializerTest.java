package com.project.json.deserialize;

import com.project.TestSources;
import com.project.elements.generators.Generator;
import com.project.elements.generators.StaticGenerator;
import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.connetions.ConnectionType;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.exceptions.LogicException;
import com.project.model.functions.ActivatedFunction;
import com.project.model.functions.FunctionController;
import com.project.model.functions.LinearFunction;
import com.project.model.functions.SigmoidFunction;
import com.project.model.neurons.Neuron;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GsonComponentConfigDeserializerTest {

    private GsonComponentDeserializer deserializer;


    @Before
    public void setUp() {
        deserializer = new GsonComponentDeserializer();
    }

    @Test
    public void deserializeTestFile() {
        String fileName = "component_json.txt";
        String jsonString = "";

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);

        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(streamReader)) {
            jsonString = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Component component = new GsonComponentDeserializer().desirealizeComponent(jsonString);
        Component expected = TestSources.getTestComponent(jsonString);

        Assert.assertEquals(expected.getSignals(), component.getSignals());
        Assert.assertEquals(expected.getElements().size(), component.getElements().size());
        //TODO Check!!!
        //Assert.assertEquals(expected.toString(), component.toString());
//        Assert.assertEquals(expected.getElements(), component.getElements());
//        Assert.assertEquals(expected, component);
    }

    @Test
    public void deserializeEmpty(){
        //TODO check!
        String jsonString = "";
            deserializer.desirealizeComponent(jsonString);

    }

    @Test
    public void deserializeNull(){
        //TODO check!
        String jsonString = null;
            deserializer.desirealizeComponent(jsonString);

    }
}