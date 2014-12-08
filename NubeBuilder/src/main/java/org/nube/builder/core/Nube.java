/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nube.builder.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author incognito
 */
public class Nube {

    private static List<File> fileFound = new ArrayList();

    public static void main(String[] args) throws IOException {

        File distro = new File("dist");
        distro.mkdir();

        //start by finding services
        File output = findDirectory(new File("src\\"), "service");
        System.out.println("Found " + output + "\n\n");

        //process domains one by one
        for (File file : output.listFiles()) {
            System.out.println("****Processing " + file.getName() + " domain ****");
            buildStaticContent(file.getName(), distro);
            compileDir(file);
            generateJars(file);
            System.out.println("**** Done processing " + file.getName() + " domain ****\n\n");
        }

    }

    public static File findDirectory(File file, String directoryName) {
        File[] files = file.listFiles();

        if (null != files) {
            for (File input : files) {
                if (!input.getName().equals(directoryName)) {
                    return findDirectory(input, directoryName);

                } else if (input.getName().equalsIgnoreCase(directoryName)) {
                    System.out.println("Found files : " + input.getName());
                    for (File tempFile : input.listFiles()) {
                        System.out.println("child " + tempFile.toString());
                    }
                    //  fileFound.addAll(Arrays.asList(input.listFiles()));
                    return input;
                }
            }
        }
        return null;
    }

    public static void compileDir(File file) {
        try {
            String command = "javac " + file + "\\*.java -classpath lib\\*";
            System.out.println("Compiling ::  " + command);
            Runtime.getRuntime().exec(command);
            System.out.println("Compile successful");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void generateJars(File file) {
        try {
            String command = "jar cvfe dist\\" + file.getName() + "\\" + file.getName() + ".jar Employee.class " + file + "\\*";
            System.out.println("Running ::  " + command);
            Runtime.getRuntime().exec(command);
            System.out.println("Jar Successful");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void buildStaticContent(String fileName, File dist) {

        //locate the static directory
        File file = new File("static");

        if (!file.exists()) {
            System.out.println("No static directory found... nothing to do here");
        } else {
            System.out.println(">> Processing static files <<");
            File staticDomain = findDirectory(file, fileName);

            if (null != staticDomain) {
                System.out.println("Process static for " + staticDomain);
                try {
                    File destDomain = new File(dist.getName() + "\\" + fileName);
                    FileUtils.copyDirectory(staticDomain, destDomain);
                } catch (IOException ex) {
                    ex.printStackTrace();;
                }
            } else {
                System.out.println("No static content for " + fileName + " domain");
            }

        }
    }
}
