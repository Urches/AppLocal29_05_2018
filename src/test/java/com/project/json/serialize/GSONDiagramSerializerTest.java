package com.project.json.serialize;

import com.project.TestSources;
import com.project.diagram.Diagram;
import com.project.diagram.DiagramComponentConfigurator;
import com.project.elements.generators.Generator;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.functions.LinearFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import com.project.run.executors.ComponentRunner;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashSet;

public class GSONDiagramSerializerTest {

    @Test
    public void testDiagramSerialize(){
        //init test data
        Component component = new Component();
        LinkedHashSet<Element> elements = new LinkedHashSet<>();
        Generator generator1 = TestSources.getTestStaticGenerator(1, 10.0);
        Generator generator2 = TestSources.getTestStaticGenerator(2,5.0);
        generator2.getPort(1).setObserved(true);
        elements.add(generator1);
        elements.add(generator2);
        Neuron neuron = TestSources.getTestNeuronBasic(3, new LinearFunction());
        neuron.getPort(3).setObserved(true);
        elements.add(neuron);
        component.setElements(elements);

        LinkedHashSet<Signal> signals = new LinkedHashSet<>();
        signals.add(TestSources.getTestSignal(1,1,3,1));
        signals.add(TestSources.getTestSignal(2,1,3,2));

        component.setSignals(signals);

        ModelSimpleTimer timer = new ModelSimpleTimer();
        timer.start(20);
        ComponentRunner runner = new ComponentRunner(component);
        runner.setTimer(timer);

        //run
        DiagramComponentConfigurator configurator = new DiagramComponentConfigurator(component);
        configurator.setExecutor(runner);
        Diagram diagram = configurator.getDiagram();

        GsonDiagramSerializer serializer = new GsonDiagramSerializer();
        String jsonString = serializer.serializeComponents(diagram);

        String expected = "{\n" +
                "  \"diagram\": [\n" +
                "    {\n" +
                "      \"title\": \"NEURON: 3\",\n" +
                "      \"type\": \"DIGITAL\",\n" +
                "      \"time\": [\n" +
                "        0,\n" +
                "        10,\n" +
                "        20\n" +
                "      ],\n" +
                "      \"values\": [\n" +
                "        50.0,\n" +
                "        50.0,\n" +
                "        50.0\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"GENERATOR: 2\",\n" +
                "      \"type\": \"DIGITAL\",\n" +
                "      \"time\": [\n" +
                "        0,\n" +
                "        10,\n" +
                "        20\n" +
                "      ],\n" +
                "      \"values\": [\n" +
                "        5.0,\n" +
                "        5.0,\n" +
                "        5.0\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //TODO!!!
        //Assert.assertEquals(expected, jsonString);
    }

}