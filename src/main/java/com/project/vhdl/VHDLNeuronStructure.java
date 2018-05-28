package com.project.vhdl;

import com.project.model.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

abstract class VHDLNeuronStructure {

    protected String pathExampleFile = "examples\\";
    protected String localTargetFile = "\\";

    abstract public File generate(int rankCount, Element element, int loadType);

    void writeFromExample(int start, int finish, FileWriter fw, String exampleFilePath) throws IOException {
        File file = new File(exampleFilePath);
        if(file.exists()){
            String[] lines = Files.lines(file.toPath()).toArray(String[]::new);
            for (int i = start; i < finish; i++) {
                fw.write(lines[i]);
            }
        } else {
            System.err.println("Example file not found!");
        }
    }

    void moduleHeadWrite(FileWriter fw) throws IOException {
        fw.write("library ieee;");
        fw.write("library altera_mf;");
        fw.write("use ieee.std_logic_1164.all;");
        fw.write("use altera_mf.altera_mf_components.all;");
        fw.write("use ieee.std_logic_unsigned.all;");
        fw.write(" ");
    }

}
