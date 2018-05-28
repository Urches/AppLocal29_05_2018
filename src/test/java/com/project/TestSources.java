package com.project;

import com.project.elements.generators.Generator;
import com.project.elements.generators.StaticGenerator;
import com.project.json.deserialize.GsonComponentDeserializer;
import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.connetions.ConnectionType;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.functions.ActivatedFunction;
import com.project.model.functions.LinearFunction;
import com.project.model.functions.SigmoidFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import com.project.run.executors.RunValue;
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

public class TestSources {

    public static Component getTestComponent(String jsonString){
        Component component = new Component();
        component.setNumber(1);
        //TODO!!!
       // component.setDescription("Тестовый компонент");
        component.setJsonScript(jsonString);

        Set<Generator> generators = new LinkedHashSet<>();
        generators.add(getTestStaticGenerator(1, 10.0));
        generators.add(getTestStaticGenerator(6, 0.0));

        Set<Neuron> neurons = new LinkedHashSet<>();
        neurons.add(getTestNeuronBasic(2, new LinearFunction()));
        neurons.add(getTestNeuronBasic(3, new SigmoidFunction()));
        neurons.add(getTestNeuronBasic(5, new LinearFunction()));

        Set<Signal> signals = new LinkedHashSet<>();
        signals.add(getTestSignal(2, 3,3,1));
        signals.add(getTestSignal(5, 3,3,2));
        signals.add(getTestSignal(1, 1,2,1));
        signals.add(getTestSignal(1, 1,5,1));
        signals.add(getTestSignal(6, 1,2,2));
        signals.add(getTestSignal(6, 1,5,2));

        Set<Element> elements = new LinkedHashSet<>();
        elements.addAll(neurons);
        elements.addAll(generators);

        component.setElements(elements);
        component.setSignals(signals);

        return component;
    }

    /**
     * Empty component just with number!
     * @return
     */
    public static Component getTestEmptyComponent(int number) {
        Component component = new Component();
        component.setNumber(number);
        //TODO !!!
        //component.setDescription("Загруженный компонент");
        return component;
    }

    /**
     * Be carefully with signal, it will be the same reference for all ports!
     * Two input digit ports without links
     * One output digit port without link
     * @param number
     * @param function
     * @return
     */
    public static Neuron getTestNeuronBasic(int number, ActivatedFunction function){
        Neuron neuron1 = new Neuron();
        neuron1.setNumber(number);
        neuron1.setActivatedFunction(function);

        Set<Port> ports = new LinkedHashSet<>();
        ports.add(getTestPort(1, PortPosition.IN, ConnectionType.DIGITAL));
        ports.add(getTestPort(2, PortPosition.IN, ConnectionType.LOGIC));
        ports.add(getTestPort(3, PortPosition.OUT, ConnectionType.DIGITAL));
        neuron1.setPorts(ports);
        return neuron1;
    }

    public static Port getTestPort(int number, PortPosition position, ConnectionType type){
        Port port = new Port();
        port.setNumber(number);
        port.setPosition(position);
        port.setType(type);
        return port;
    }


    public static Generator getTestStaticGenerator(int number, Double value){
        StaticGenerator generator = new StaticGenerator();
        generator.setNumber(number);
        generator.setValue(value);
        Set<Port> ports = new LinkedHashSet<>();
        ports.add(getTestPort(1, PortPosition.OUT, ConnectionType.DIGITAL));
        generator.setPorts(ports);
        return generator;
    }

    public static Signal getTestSignal(int fromElement, int fromPort, int toElement, int toPort) {
        Signal signal = new Signal();
        signal.setFromElementNumber(fromElement);
        signal.setFromPortNumber(fromPort);
        signal.setToElementNumber(toElement);
        signal.setToPortNumber(toPort);
        signal.setValue(10.0);
        return signal;
    }

    public static Component deserializeTestFile(String fileName) {
        String jsonString = "";

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);

        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(streamReader)) {
            jsonString = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GsonComponentDeserializer().desirealizeComponent(jsonString);
    }

    public static RunValue getRunValue(Element element, Port port, Double value, long time) {
        RunValue runValue = new RunValue();
        runValue.setPort(port);
        runValue.setElement(element);
        runValue.setValue(value);
        runValue.setTime(time);
        return runValue;
    }
}
