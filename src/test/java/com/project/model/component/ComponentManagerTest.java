package com.project.model.component;

import com.project.TestSources;
import com.project.elements.generators.Generator;
import com.project.model.Element;
import com.project.model.functions.LinearFunction;
import com.project.model.neurons.Neuron;
import com.project.model.signals.Signal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ComponentManagerTest {

    @Test
    public void getLayers(){
        //init
        Component component = TestSources.deserializeTestFile("component_json.txt");
        ComponentManager manager = new ComponentManager();
        manager.setComponent(component);

        Set<Element> firstLayer = manager.getFirstLayer();
        Set<Element> expected = new LinkedHashSet<>();
        expected.add(component.getElement(1));
        expected.add(component.getElement(6));
        Assert.assertEquals(expected, firstLayer);

//        Set<Element> nextLayer = manager.getNextLayer(firstLayer);
//        expected = new LinkedHashSet<>();
//        expected.add(component.getElement(2));
//        expected.add(component.getElement(5));
//        Assert.assertEquals(expected, nextLayer);
//
//        nextLayer = manager.getNextLayer(nextLayer);
//        expected = new LinkedHashSet<>();
//        expected.add(component.getElement(3));
//        Assert.assertEquals(expected, nextLayer);
    }

    @Test
    public void getExecutionOrder(){
        //init
        Component component = TestSources.deserializeTestFile("component_json.txt");
        ComponentManager manager = new ComponentManager();
        manager.setComponent(component);
        Element[][] executionOrderedElements = manager.getExecutionOrderedElements();

        Element[][] expected = new Element[3][2];

        Element[] layer = new Element[2];
        layer[0] = component.getElement(1);
        layer[1] = component.getElement(6);
        expected[0] = layer;

        layer = new Element[2];
        layer[0] = component.getElement(2);
        layer[1] = component.getElement(5);
        expected[1] = layer;

        layer = new Element[1];
        layer[0] = component.getElement(3);
        expected[2] = layer;

        Assert.assertEquals(expected.length, executionOrderedElements.length);
        Assert.assertEquals(expected[0].length, executionOrderedElements[0].length);
        Assert.assertEquals(expected[1].length, executionOrderedElements[1].length);
        Assert.assertEquals(expected[2].length, executionOrderedElements[2].length);
//        System.out.println(Arrays.toString(expected));
//        System.out.println(Arrays.toString(executionOrderedElements));
//        Assert.assertArrayEquals(expected, executionOrderedElements);
    }

}