package ru.evgeny;

import ru.evgeny.merge.FileMerger;
import ru.evgeny.merge.SortingSettings;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        try {
            SortingSettings settings = SortingSettings.getInstance(args);
            SortingSettings.printInfo(settings);
            FileMerger merger = new FileMerger(settings);
            merger.mergeFiles();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}