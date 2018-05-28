package com.project.vhdl;

import com.project.model.component.Component;
import com.project.utils.FileIOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VHDLRootFile {

    //TODO Also mock!
    public File generate(Component component) {
        String localTargetFile = "\\component" + component.getNumber() + ".vhdl";

        String content = "entity component" + component.getNumber() + " is\n" +
                "port(f0 :in std_logic; -- reference frequency\n" +
                "\tx  : in std_logic_vector (" + 8 + " downto 1);\n" +
                "\tNtheta  : in std_logic_vector (" + 8 + " downto 1);\n" +
                "\tfy  : out std_logic );\n";

        try (FileWriter fw = new FileWriter(localTargetFile)) {
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(localTargetFile);
    }

}
