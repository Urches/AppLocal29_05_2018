package com.project.vhdl;

import com.project.elements.Delay;
import com.project.model.Element;
import com.project.model.ElementType;
import com.project.model.component.Component;
import com.project.model.neurons.Neuron;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VHDLStructureConfigurator {

    //TODO this is mock
     private List<ElementType> configuredTypes =
             Arrays.asList(ElementType.DELAY, ElementType.NEURON);

     private List<VHDLNeuronStructure> neuronStructures =
             Arrays.asList(new VHDLNeuronTauCode(), new VHDLCodeTauNeuronStructure(),
                     new VHDLNeuronDY(), new VHDLFileCodeFStructure());

    public Set<File> configure(Component component){
        Set<Element> elements = component.getElements();
        //TODO Mock again!
        Set<File> files = elements.stream()
                .filter(element -> configuredTypes.contains(element.getType()))
                .map(element -> neuronStructures.get(0).generate(8, element, 1))
                .collect(Collectors.toSet());
        files.add(new VHDLRootFile().generate(component));
        return files;
    }
}
