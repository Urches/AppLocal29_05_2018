package com.project.vhdl;

import com.project.model.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VHDLCodeTauNeuronStructure extends VHDLNeuronStructure {

    public File generate(int rankCount, Element element, int loadType) {
        //TODO
        String name = element.getType().toString().toLowerCase() + element.getNumber();
        //smell!
        String pathExampleFile = this.pathExampleFile +"\\exampleCodeTau" + loadType + ".vhdl";
        String localTargetFile = this.localTargetFile + "\\" + name +".vhd";

        try (FileWriter fw = new FileWriter(localTargetFile)) {
            moduleHeadWrite(fw);
            fw.write("entity " + name + " is");
            fw.write("port(f0 :in std_logic; -- reference frequency");
            fw.write("\tNx  : in std_logic_vector (" + rankCount + " downto 1);");
            fw.write("\tNtheta  : in std_logic_vector (" + rankCount + " downto 1);");
            fw.write("\ttau  : out std_logic);");
            fw.write("end " + name + ";");
            fw.write("architecture behav of " + name + " is");
            fw.write("signal cnt : std_logic_vector(" + rankCount + " downto 1);");
            fw.write("signal cnt2 : std_logic_vector(" + rankCount + " downto 1);");
            writeFromExample(16, 59, fw, pathExampleFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(localTargetFile);
    }
}
