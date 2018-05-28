package com.project.vhdl;

import com.project.model.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VHDLNeuronTauCode extends VHDLNeuronStructure {

    public File generate(int rankCount, Element element, int loadType) {
        //TODO
        String name = element.getType().toString().toLowerCase() + element.getNumber();
        //smell!
        String pathExampleFile = this.pathExampleFile + "exampleCodeTau" + loadType + ".vhdl";
        String localTargetFile = this.localTargetFile + name +".vhd";

        try (FileWriter fw = new FileWriter(localTargetFile)) {
            moduleHeadWrite(fw);
            fw.write("entity " + name + " is");
            fw.write("port(f0 :in std_logic; -- reference frequency");
            fw.write("\ttau  : in std_logic;");
            fw.write("\tNx  : in std_logic_vector (" + rankCount + " downto 1);");
            fw.write("\tNy  : out std_logic_vector (" + rankCount + " downto 1));");
            fw.write("end " + name + ";");

            fw.write("architecture behav of " + name + " is");
            fw.write("signal cnt : std_logic_vector(" + rankCount + " downto 1);");
            fw.write("signal cnt2 : std_logic_vector(" + rankCount + " downto 1);");

            writeFromExample(18, 25, fw, pathExampleFile);

            fw.write("else if (tau = '0') then cnt <=" +  (char)34);
            for (int i = 0; i < rankCount; i++)
            {
                fw.write("0");
            }
            fw.write((char)34 + ";");

            writeFromExample(26, 52, fw, pathExampleFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(localTargetFile);
    }
}


