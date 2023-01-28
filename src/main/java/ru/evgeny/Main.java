package ru.evgeny;

import ru.evgeny.merge.FileMerger;
import ru.evgeny.merge.SortingSettings;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        try {
            SortingSettings settings = SortingSettings.getInstance(args);
            FileMerger merger = new FileMerger(settings);
            merger.mergeFiles();
            SortingSettings.printInfo(settings);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
