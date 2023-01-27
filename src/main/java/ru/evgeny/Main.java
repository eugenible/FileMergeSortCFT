package ru.evgeny;

import ru.evgeny.merge.FileMerger;
import ru.evgeny.merge.SortingSettings;

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
        try {
            SortingSettings settings = SortingSettings.getInstance(args);
            printInfo(settings);
            FileMerger merger = new FileMerger(settings);
            merger.mergeFiles();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}