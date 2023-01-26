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

    // Алгоритм работы: создается n Reader-ов, где n - кол-во входных файлов, из каждого reader-а считывается одно
    // значение и помещается в массив "отбора", из которого затем выбирается подходящее по условиям сортировки и
    // записывается в выходной файл. В массив "отбора" далее помещается новое значение из соответствующего ридера.
    // Действия повторяются до тех пор, пока соблюдается порядок сортировки и есть данные для чтения.
    public void mergeFiles() {
        BufferedReader[] readers = getFileReaders(settings.getInputFiles());
        if (readers == null) return;

        boolean[] stopReading = new boolean[readers.length];
        String[] inputLines = new String[readers.length];
        String[] previousLines = new String[readers.length];  // Для проверки правильности сортировки в файлах

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(settings.getOutputFile(), false))) {
            while (true) {
                String lineToWrite = getLineToWrite(readers, stopReading, inputLines, previousLines);
                if (lineToWrite == null) break;
                writer.write(lineToWrite);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file: " + e.getMessage());
        }

        closeReaders(readers);
    }

    private String getLineToWrite(BufferedReader[] readers, boolean[] stopReading, String[] inputLines,
                                  String[] previousLines) throws IOException {
        fillInputLines(inputLines, readers, stopReading, previousLines);
        return findBestLine(inputLines);
    }

    private void fillInputLines(String[] inputLines, BufferedReader[] readers, boolean[] stopReading,
                                String[] previousLines) throws IOException {
        for (int i = 0; i < readers.length; ++i) {
            if (inputLines[i] != null) continue;
            String line = null;
            boolean isInvalidLine = true;
            while (!stopReading[i] && isInvalidLine) {
                if ((line = readers[i].readLine()) == null) {
                    stopReading[i] = true;
                    continue;
                }
                if (isValid(line)) {
                    inputLines[i] = line;
                    isInvalidLine = false;
                }
            }

            if (line != null) checkReaderDataOrder(inputLines, i, stopReading, previousLines);
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

    private void checkReaderDataOrder(String[] inputLines, int index, boolean[] stopReading,
                                      String[] previousLines) {
        String prevLine = previousLines[index];
        String currLine = inputLines[index];
        if (prevLine == null) {
            previousLines[index] = currLine;
            return;
        }

        if (!satisfiesOrder(currLine, prevLine, settings.getOrder())) {
            inputLines[index] = null;
            stopReading[index] = true;
        } else {
            previousLines[index] = currLine;
        }
    }

    // Возвращает true, если current следует за previous в соотв. с порядком order, иначе false.
    private boolean satisfiesOrder(String current, String previous, Order order) {
        int currNum, prevNum;
        if (settings.getType() == DataType.INTEGER) {
            currNum = Integer.parseInt(current);
            prevNum = Integer.parseInt(previous);
            return (order == Order.ASC) ? currNum >= prevNum : currNum <= prevNum;
        }
        return (settings.getOrder() == Order.ASC) ? current.compareTo(previous) >= 0 : current.compareTo(previous) <= 0;
    }

    private boolean isValid(String line) {
        if (line.contains(" ")) return false;
        return settings.getType() != DataType.INTEGER || line.matches("^[+-]?[0-9]+$");
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
}
