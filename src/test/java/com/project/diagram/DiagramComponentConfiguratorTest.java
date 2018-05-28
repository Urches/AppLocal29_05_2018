package com.project.diagram;

import com.project.TestSources;
import com.project.elements.generators.Generator;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.functions.LinearFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import com.project.run.executors.ComponentRunner;
import com.project.run.executors.RunValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;
import static com.project.TestSources.*;

public class DiagramComponentConfiguratorTest {

   @Test
    public void testGetDiagram() {
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
        timer.start(10);
        ComponentRunner runner = new ComponentRunner(component);
        runner.setTimer(timer);

        //run
        DiagramComponentConfigurator configurator = new DiagramComponentConfigurator(component);
        configurator.setExecutor(runner);
        Diagram diagram = configurator.getDiagram();

        //init expected data
       Set<RunValue> expectedValues = new LinkedHashSet<>();
       expectedValues.add(getRunValue(neuron, neuron.getPort(3), 15.0, 0));
       expectedValues.add(getRunValue(generator2, generator2.getPort(1), 5.0,0));

       expectedValues.add(getRunValue(neuron, neuron.getPort(3), 15.0, 10));
       expectedValues.add(getRunValue(generator2, generator2.getPort(1), 5.0,10));


       Diagram expected = new Diagram(expectedValues);

       Assert.assertEquals(expected.getLines().size(), diagram.getLines().size());
       Assert.assertTrue(expected.getLines().containsAll(diagram.getLines()));
       Assert.assertEquals(expected, diagram);
    }


}