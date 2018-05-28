package com.project.vhdl;

import com.project.model.Element;
import com.project.model.component.Component;
import com.project.model.neurons.Neuron;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class VHDLStructureConfiguratorTest {


    private VHDLStructureConfigurator configurator = null;
    private Component component = null;
    @Before
    public void setUp() throws Exception {
        configurator = new VHDLStructureConfigurator();
        component = new Component();
        HashSet<Element> elements = new HashSet<>();
        Neuron neuron1 = new Neuron();
        neuron1.setNumber(1);

        Neuron neuron2 = new Neuron();
        neuron2.setNumber(2);

        Neuron neuron3 = new Neuron();
        neuron3.setNumber(3);

        elements.add(neuron1);
        elements.add(neuron2);
        elements.add(neuron3);
        component.setNumber(20204);
        component.setElements(elements);
    }

    @Test
    public void configureTest() {
        try {
            configurator.configure(this.component);
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }

}