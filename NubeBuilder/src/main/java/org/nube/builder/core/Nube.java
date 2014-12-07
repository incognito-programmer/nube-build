/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nube.builder.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author incognito
 */
public class Nube {

    private static final List<File> fileFound = new ArrayList();

    static void main(String[] args) {
        findServices(new File("C:\\test"));
        compileDir(fileFound.get(0));
        generateJars(fileFound.get(0));
    }

    public static void findServices(File file) {
        File[] files = file.listFiles();
        if (null != files) {
            for (File input : files) {
//                System.out.println("File is : " + input.getName());
                if (!input.getName().equals("service")) {
                    findServices(input);
                } else if (input.getName().equalsIgnoreCase("service")) {
//                    System.out.println("Do i ever get here : " + input.getName());
                    fileFound.add(input);
                }
            }
        }
    }

    public static void compileDir(File file) {
        try {
            String command = "javac " + file.listFiles()[0] + "\\*.java";
            System.out.println("Running ::  " + command);
            Runtime.getRuntime().exec(command);
            System.out.println("Compile successful");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void generateJars(File file) {
        try {
            String command = "jar cvf C:\\test\\" + file.getName() + ".jar " + file.listFiles()[0] + "\\*";
            System.out.println("Running ::  " + command);
            Runtime.getRuntime().exec(command);
            System.out.println("Jar Successful");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
