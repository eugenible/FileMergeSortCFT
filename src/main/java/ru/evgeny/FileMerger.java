package ru.evgeny;

import java.io.*;
import java.util.List;

public class FileMerger {
    private SortingSettings settings;

    public FileMerger(SortingSettings settings) {
        this.settings = settings;
    }

    public SortingSettings getSettings() {
        return settings;
    }

    public void setSettings(SortingSettings settings) {
        this.settings = settings;
    }

    private BufferedReader[] getFileReaders(List<File> inputFiles) {
        int numOfFiles = inputFiles.size();
        BufferedReader[] readers = new BufferedReader[numOfFiles];
        try {
            for (int i = 0; i < numOfFiles; ++i) {
                readers[i] = new BufferedReader(new FileReader(inputFiles.get(i)));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            closeReaders(readers);
            readers = null;
        }
        return readers;
    }

    private void closeReaders(BufferedReader[] readers) {
        for (BufferedReader reader : readers) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private boolean isValid(String line) {
        if (line.contains(" ")) return false;
        return settings.getType() != DataType.INTEGER || line.matches("^[+-]?[0-9]+$");
    }

    private boolean satisfiesOrder(String current, String previous, Order order) {
        int currNum, prevNum;
        if (settings.getType() == DataType.INTEGER) {
            currNum = Integer.parseInt(current);
            prevNum = Integer.parseInt(previous);
            return (order == Order.ASC) ? currNum >= prevNum : currNum <= prevNum;
        }
        return (settings.getOrder() == Order.ASC) ? current.compareTo(previous) >= 0 : current.compareTo(previous) <= 0;
    }


    private void processIfDataOrderBroken(String[] inputLines, int index, boolean[] stopReading,
                                          String[] previousValues) {
        String prevValue = previousValues[index];
        String currValue = inputLines[index];
        if (prevValue == null) {
            previousValues[index] = currValue;
            return;
        }

        if (!satisfiesOrder(currValue, prevValue, settings.getOrder())) {
            inputLines[index] = null;
            stopReading[index] = true;
        } else {
            previousValues[index] = currValue;
        }
    }

    private void fillInputLines(String[] inputLines, BufferedReader[] readers, boolean[] stopReading,
                                String[] previousValues) throws IOException {
        for (int i = 0; i < inputLines.length; ++i) {
            if (inputLines[i] != null) continue;
            String line = null;
            boolean isValidLine = false;
            while (!stopReading[i] && !isValidLine) {
                if ((line = readers[i].readLine()) == null) {
                    stopReading[i] = true;
                    continue;
                }
                if (isValid(line)) {
                    inputLines[i] = line;
                    isValidLine = true;
                }
            }

            if (line != null) processIfDataOrderBroken(inputLines, i, stopReading, previousValues);
        }
    }

    private String findBestLine(String[] inputLines) {
        String bestValue = null;
        int bestElementIndex = -1;

        for (int i = 0; i < inputLines.length; ++i) {
            if (inputLines[i] == null) continue;

            if (bestValue == null) {
                bestValue = inputLines[i];
                bestElementIndex = i;
            }

            if (satisfiesOrder(inputLines[i], bestValue, settings.getOrder().opposite())) {
                bestValue = inputLines[i];
                bestElementIndex = i;
            }
        }

        if (bestValue != null) inputLines[bestElementIndex] = null;

        return bestValue;
    }

    private String chooseLine(BufferedReader[] readers, boolean[] stopReading, String[] inputLines,
                              String[] previousValues) throws IOException {
        fillInputLines(inputLines, readers, stopReading, previousValues);
        return findBestLine(inputLines);
    }

    // Алгоритм работы: создается n Reader-ов, где n - кол-во входных файлов, из каждого reader-а считывается одно
    // значение и помещается в массив "отбора", из которого затем выбирается подходящее по условиям сортировки и
    // записывается в выходной файл. В массив "отбора" далее помещается новое значение из соответствующего ридера.
    // Действия повторяются до тех пор, пока соблюдается порядок сортировки и есть данные для чтения.
    public void mergeFiles() {
        BufferedReader[] readers = getFileReaders(settings.getInputFiles());
        if (readers == null) return;

        boolean[] stopReading = new boolean[readers.length];
        String[] inputLines = new String[readers.length];
        String[] previousValues = new String[readers.length];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(settings.getOutputFile(), false))) {
            while (true) {
                String lineToWrite = chooseLine(readers, stopReading, inputLines, previousValues);
                if (lineToWrite == null) break;
                writer.write(lineToWrite);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file: " + e.getMessage());
        }

        closeReaders(readers);
    }

}
