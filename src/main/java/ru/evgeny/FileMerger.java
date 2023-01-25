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

    public BufferedReader[] getFileReaders(List<File> inputFiles) {
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

    // Проверка, есть ли пробелы в строке или содержимое строки не соответствует типу данных.
    private boolean isValid(String line) {
        if (line.contains(" ")) return false;
        return settings.getType() != DataType.INTEGER || line.matches("^[+-]?[0-9]+$");
    }

    // Возвращает true, если current следует за previous в соответствии с указанным порядком
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
        String prevStrValue = previousValues[index];
        String currValue = inputLines[index];
        if (prevStrValue == null) {
            previousValues[index] = currValue;
            return;
        }

        if (!satisfiesOrder(currValue, prevStrValue, settings.getOrder())) {
            inputLines[index] = null;
            stopReading[index] = true;
        } else {
            previousValues[index] = currValue;
        }
    }

    public void fillInputLines(String[] inputLines, BufferedReader[] readers, boolean[] stopReading,
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

    public String chooseLine(BufferedReader[] readers, boolean[] stopReading, String[] inputLines,
                             String[] previousValues) throws IOException {
        fillInputLines(inputLines, readers, stopReading,
                previousValues);  // Заполнили массив значений для дальнейшего выбора ближайшего подходящего значения
        // Выставить в null самый подходящий элемент
        String bestStrValue = null;
        int bestElementIndex = -1;

        // Выставить null в соотв. лементе массива
        for (int i = 0; i < inputLines.length; ++i) {
            if (inputLines[i] == null) continue;
            // Выставляем минимальное значение, равное первому элементу, который ne null
            if (bestStrValue == null) {
                bestStrValue = inputLines[i];
                bestElementIndex = i;
            }

            // Сравнение чисел или строк соответственно
            if (satisfiesOrder(inputLines[i], bestStrValue, settings.getOrder().opposite())) {
                bestStrValue = inputLines[i];
                bestElementIndex = i;
            }
        }

        if (bestElementIndex != -1) inputLines[bestElementIndex] = null;
        return bestStrValue;
    }


    public boolean mergeFiles() {
        BufferedReader[] readers = getFileReaders(settings.getInputFiles());
        if (readers == null) return false;
        // С пом. этого массива обозачаем, нужно ли читать очередную строку из ридера (из-за достижения EOF или
        // нарушения сортировки в соответствующем ридеру файле строку читать не надо)
        boolean[] stopReading = new boolean[readers.length];
        // Массив строк, из которых будет выбрано значение для записи в выходной файл. Каждый элемент
        // получен из соответствующего индексу ридера. В ячейку, откуда была взята строка для записи в файл, в
        // будущем будет положено следующее значение, прочитанное из соответствущего ридера.
        String[] inputLines = new String[readers.length];
        // Массив последних получ. из ридеров значений для сравнения с только что прочитанным: используется для проверки
        // правильности сортировки во входных файлах
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
            return false;
        }

        closeReaders(readers);
        return true;
    }

}
