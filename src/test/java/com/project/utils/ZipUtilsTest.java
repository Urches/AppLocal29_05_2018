package com.project.utils;

import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ZipUtilsTest {

    private ZipUtils utils;
    @Before
    public void setUp() throws Exception {

        utils = new ZipUtils();
    }

    @Test
    public void createZip() {
        try {
            Set<File> files = new HashSet<>();
            files.add(new File("\\src\\test\\resources\\forarr.vhdl"));
            utils.createZip("\\test\\resources\\arrtest.zip", files);
        } catch (Exception e){
            throw new AssertionError(e);
        }

    }
}