package com.project.utils;

import java.io.*;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public void createZip(String targetPath, Set<File> files) {
        //@example targetPath = "F:/tmp.zip"
        ZipOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(targetPath));
            for (File file : files) {
                in = new FileInputStream(file);
                // name the file inside the zip  file
                out.putNextEntry(new ZipEntry(file.getName()));

                // buffer size
                byte[] b = new byte[1024];
                int count;
                while ((count = in.read(b)) > 0) {
                    out.write(b, 0, count);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
