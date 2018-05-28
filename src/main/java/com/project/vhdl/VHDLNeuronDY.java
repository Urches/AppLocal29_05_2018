package com.project.vhdl;

import com.project.model.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VHDLNeuronDY extends VHDLNeuronStructure {

    public File generate(int rankCount, Element element, int loadType) {
        //TODO
        String name = element.getType().toString().toLowerCase() + element.getNumber();
        //smell!
        String pathExampleFile = this.pathExampleFile +"\\exampleCodeTau" + loadType + ".vhdl";
        String localTargetFile = this.localTargetFile + "\\" + name +".vhd";

        try (FileWriter fw = new FileWriter(localTargetFile)) {
            String[] freq = new String[rankCount];

            for (int i = 0; i < rankCount; i++) {
                freq[i] = "f_" + Math.pow(2, i + 1);
            }
            moduleHeadWrite(fw);
            fw.write("entity " + name + " is");
            fw.write("port(f0 :in std_logic; -- reference frequency");
            fw.write("\tw  : in std_logic_vector (" + rankCount + " downto 1);");
            fw.write("\t");

            for (int i = 0; i < rankCount - 1; i++) {
                fw.write(freq[i] + "_o, ");
            }
            fw.write(freq[rankCount - 1] + "_o : out std_logic; -- division frequency ");

            fw.write("\tshim : out std_logic; -- SHIM result");
            fw.write("\tchim : out std_logic  ); -- CHIM result");
            fw.write("end " + name + ";");
            fw.write("\r");
            fw.write("architecture behav of " + name + " is");

            fw.write("signal cnt : std_logic_vector(" + rankCount + " downto 1);");
            fw.write("signal cnt_t : std_logic_vector(" + rankCount + " downto 1);");
            fw.write("signal ");

            for (int i = 0; i < rankCount - 1; i++) {
                fw.write(freq[i] + ", ");
            }
            fw.write(freq[rankCount - 1] + ": std_logic;");


            writeFromExample(20, 38, fw, pathExampleFile);

            for (int i = 0; i < rankCount; i++) {
                fw.write(freq[i] + " <= w(" + (rankCount - i) + ") and cnt(" + (i + 1) + ") and (not cnt_t(" + (i + 1) + "));");
            }
            fw.write("\r");
            fw.write("shim_in <= ");
            for (int i = 0; i < rankCount - 1; i++) {
                fw.write(freq[i] + " or ");
            }
            fw.write(freq[rankCount - 1] + ";");
            fw.write("chim <= f0 and shim_in;");
            fw.write("\r");
            for (int i = 0; i < rankCount; i++) {
                fw.write(freq[i] + "_o <= " + freq[i] + ";");
            }
            fw.write("shim <= shim_in;");
            fw.write("\r");
            fw.write("end behav;");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(localTargetFile);
    }
}
