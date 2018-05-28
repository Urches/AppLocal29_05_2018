package com.project.run.executors;

import com.project.TestSources;
import com.project.elements.generators.Generator;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.json.deserialize.GsonComponentDeserializer;
import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.functions.LinearFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.TestSources.*;

public class ComponentRunnerTest {

    @Test
    public void testExecute(){

        Component component = new Component();
        LinkedHashSet<Element> elements = new LinkedHashSet<>();
        Generator generator1 = TestSources.getTestStaticGenerator(1, 10.0);
        Generator generator2 = TestSources.getTestStaticGenerator(2,5.0);
        elements.add(generator1);
        elements.add(generator2);
        Neuron neuron = TestSources.getTestNeuronBasic(3, new LinearFunction());
        elements.add(neuron);
        component.setElements(elements);

        LinkedHashSet<Signal> signals = new LinkedHashSet<>();
        signals.add(TestSources.getTestSignal(1,1,3,1));
        signals.add(TestSources.getTestSignal(2,1,3,2));

        component.setSignals(signals);

        ModelSimpleTimer timer = new ModelSimpleTimer();
        timer.start(10);

        ComponentRunner runner = new ComponentRunner(component);
        runner.setTimer(timer);

        //test
        Set<RunValue> runValues = runner.execute();

        Set<RunValue> expected = new LinkedHashSet<>();
        expected.add(getRunValue(generator1, generator1.getPort(1), 10.0, 0));
        expected.add(getRunValue(generator2, generator2.getPort(1), 5.0,0));
        expected.add(getRunValue(neuron, neuron.getPort(1), 10.0, 0));
        expected.add(getRunValue(neuron, neuron.getPort(2), 5.0,0));
        expected.add(getRunValue(neuron, neuron.getPort(3), 50.0,0));

        expected.add(getRunValue(generator1, generator1.getPort(1), 10.0, 10));
        expected.add(getRunValue(generator2, generator2.getPort(1), 5.0,10));
        expected.add(getRunValue(neuron, neuron.getPort(1), 10.0, 10));
        expected.add(getRunValue(neuron, neuron.getPort(2), 5.0,10));
        expected.add(getRunValue(neuron, neuron.getPort(3), 50.0,10));

        Assert.assertEquals(expected.size(), runValues.size());
        Assert.assertEquals(expected, runValues);
    }

    public void testExecuteTwoNeuronsOneGen(){
        //init
        Component component = new Component();
        Neuron neuron1 = TestSources.getTestNeuronBasic(1, new LinearFunction());
        Neuron neuron2 = TestSources.getTestNeuronBasic(2, new LinearFunction());
        Generator generator = TestSources.getTestStaticGenerator(3, 10.0);

        //neuron1.getPort(1).setSignal(generator.getSignal());
       // neuron2.getPort(1).setSignal(neuron1.getPort(3).getSignal());

        Set<Element> elements = new LinkedHashSet<>();
        elements.add(neuron1);
        elements.add(neuron2);
        component.setElements(elements);

        ModelSimpleTimer timer = new ModelSimpleTimer();
        timer.start(10);

        ComponentRunner runner = new ComponentRunner(component);
        runner.setTimer(timer);

        //test
        Set<RunValue> runValues = runner.execute();
        Set<RunValue> expected = new HashSet<>();
        Neuron[] neurons = component.getElements().stream().toArray(Neuron[]::new);

        expected.add(getRunValue(neurons[0], neurons[0].getPort(1), 10.0, 0));
        expected.add(getRunValue(neurons[0], neurons[0].getPort(2), 10.0,0));
        expected.add(getRunValue(neurons[0], neurons[0].getPort(3), 100.0,0));

        expected.add(getRunValue(neurons[1], neurons[1].getPort(1), 100.0,0));
        expected.add(getRunValue(neurons[1], neurons[1].getPort(2), 10.0,0));
        expected.add(getRunValue(neurons[1], neurons[1].getPort(3), 1000.0,0));

        expected.add(getRunValue(neurons[0], neurons[0].getPort(1), 10.0, 10));
        expected.add(getRunValue(neurons[0], neurons[0].getPort(2), 10.0,10));
        expected.add(getRunValue(neurons[0], neurons[0].getPort(3), 100.0,10));

        expected.add(getRunValue(neurons[1], neurons[1].getPort(1), 100.0,10));
        expected.add(getRunValue(neurons[1], neurons[1].getPort(2), 10.0,10));
        expected.add(getRunValue(neurons[1], neurons[1].getPort(3), 1000.0,10));

        Assert.assertEquals(expected.size(), runValues.size());
        Assert.assertEquals(expected, runValues);
    }

//    @Test
//    public void testExecuteTestFileComponent(){
//        String fileName = "component_json.txt";
//        String jsonString = "";
//
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        InputStream is = classloader.getResourceAsStream(fileName);
//
//        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
//        try (BufferedReader reader = new BufferedReader(streamReader)) {
//            jsonString = reader.lines().collect(Collectors.joining());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Component component = new GsonComponentDeserializer().desirealizeComponent(jsonString);
//
//        ComponentRunner runner = new ComponentRunner(component);
//        Set<RunValue> execute = runner.execute();
//        System.out.println(execute);
//
//    }
}