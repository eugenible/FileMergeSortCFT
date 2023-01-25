package ru.evgeny;

import java.io.File;
import java.io.IOException;


public class Main {

    public static void printInfo(SortingSettings settings) {
        System.out.println("Order type: " + settings.getOrder());
        System.out.println("Data type: " + settings.getType());
        System.out.println("Output file: " + settings.getOutputFile().getName());
        for (File f : settings.getInputFiles()) {
            System.out.println("Input file: " + f.getName());
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        try {
            SortingSettings settings = SortingSettings.getInstance(args);

            printInfo(settings);

        } catch (InvalidLineArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}