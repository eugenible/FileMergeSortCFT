package ru.evgeny;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SortingSettings settings = SortingSettings.getInstance(args);
        } catch (InvalidLineArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}