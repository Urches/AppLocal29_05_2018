package com.project.run.executors;

import com.project.TestSources;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.connetions.Port;
import com.project.model.connetions.PortPosition;
import com.project.model.functions.LinearFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import static com.project.TestSources.*;


public class NeuronRunnerTest {

//    private NeuronRunner runner;
//    private Neuron neuron;
//
//    @Before
//    public void setUp() {
//
//
//        Set<Element> neurons = new HashSet<>();
//        neurons.add(TestSources.getTestNeuronBasic(1, new LinearFunction()));
//        neurons.add(TestSources.getTestStaticGenerator(2, 10.0));
//
//        Component component = new Component();
//        component.setElements(neurons);
//
//        Set<Signal> signals = new LinkedHashSet<>();
//        signals.add(TestSources.getTestSignal(2,1,1,1));
//        signals.add(TestSources.getTestSignal(2,1,1,2));
//        signals.add(TestSources.getTestSignal(1,3,3,2));
//
//        component.setSignals(signals);
//        runner = new NeuronRunner(neuron);
//        runner.setComponent(component);
//    }
//
//    @Test
//    public void testExecuteOneIter(){
//        ModelSimpleTimer modelSimpleTimer = new ModelSimpleTimer();
//        modelSimpleTimer.start();
//        modelSimpleTimer.hasNext();
//        runner.setTimer(modelSimpleTimer);
//
//        Set<RunValue> runValues = runner.execute();
//
//        RunValue runValue = new RunValue();
//        runValue.setTime(0);
//        runValue.setElement(neuron);
//        runValue.setPort(neuron.getPort(1));
//        runValue.setValue(10.0);
//
//        RunValue runValue2 = new RunValue();
//        runValue2.setTime(0);
//        runValue2.setElement(neuron);
//        runValue2.setPort(neuron.getPort(2));
//        runValue2.setValue(10.0);
//
//        RunValue runValue3 = new RunValue();
//        runValue3.setTime(0);
//        runValue3.setElement(neuron);
//        runValue3.setPort(neuron.getPort(3));
//        runValue3.setValue(100.0);
//
//        Set<RunValue> expected = new LinkedHashSet<>();
//        expected.add(runValue);
//        expected.add(runValue2);
//        expected.add(runValue3);
//
//        Assert.assertEquals(expected, runValues);
//    }
//
//    @Test
//    public void testExecuteThreeIter(){
//        ModelSimpleTimer modelSimpleTimer = new ModelSimpleTimer();
//        modelSimpleTimer.start(20);
//        runner.setTimer(modelSimpleTimer);
//        Set<RunValue> runValues =  new HashSet<>();
//        Set<RunValue> expected = new HashSet<>();
//        while (modelSimpleTimer.hasNext()){
//            runValues.addAll(runner.execute());
//            expected.add(getRunValue(neuron, neuron.getPort(1), 10.0, modelSimpleTimer.getCurrentModelTime()));
//            expected.add(getRunValue(neuron, neuron.getPort(2), 10.0, modelSimpleTimer.getCurrentModelTime()));
//            expected.add(getRunValue(neuron, neuron.getPort(3), 100.0, modelSimpleTimer.getCurrentModelTime()));
//        }
//        Assert.assertEquals(expected.size(), runValues.size());
//        Assert.assertEquals(expected, runValues);
//    }
}