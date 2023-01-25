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


//    private void printAllReaders(BufferedReader[] readers) {
//        for (BufferedReader reader : readers) {
//            String line = null;
//            try {
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }

    // Проверка, есть ли пробелы в строке или содержимое строки не соответствует типу данных.
    private boolean isValid(String line) {
        if (line.contains(" ")) return false;
        return settings.getType() != DataType.INTEGER || line.matches("^[+-]?[0-9]+$");
    }

    // Возвращает true, если current следует за previous в соответствии с указанным порядком
    private boolean satisfiesOrder(String current, String previous) {
        int currNum, prevNum;
        if (settings.getType() == DataType.INTEGER) {
            currNum = Integer.parseInt(current);
            prevNum = Integer.parseInt(previous);
            return (settings.getOrder() == Order.ASC) ? currNum >= prevNum : currNum <= prevNum;
        }

        return (settings.getOrder() == Order.ASC) ? current.compareTo(previous) >= 0 : current.compareTo(previous) <= 0;
    }

    private void processIfDataOrderBroken(String[] inputLines, int index, boolean[] stopReading,
                                          String[] previousValues) {
        String prevStrValue = previousValues[index];
        String currValue = inputLines[index];
        if (!satisfiesOrder(currValue, prevStrValue)) {
            inputLines[index] = null;
            stopReading[index] = true;
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

            processIfDataOrderBroken(inputLines, i, stopReading, previousValues);
        }
    }

    public String chooseLine(BufferedReader[] readers, boolean[] stopReading, String[] inputLines,
                             String[] previousValues) throws IOException {
        fillInputLines(inputLines, readers, stopReading,
                previousValues);  // Заполнили массив для дальнейшего выбора миним. значения
        // Выставить в null наименьший элемент
        String minStrValue = null;
        int minIntValue = Integer.MAX_VALUE;
        int minElementIndex = -1;

        DataType type = settings.getType();
        // Выставить null в соотв. лементе массива
        for (int i = 0; i < inputLines.length; ++i) {
            if (inputLines[i] == null) continue;

            // Выставляем минимальное значение, равное первому элементу, который ne null
            if (minStrValue == null) {
                minStrValue = inputLines[i];
                minElementIndex = i;
                if (type == DataType.INTEGER) minIntValue = Integer.parseInt(minStrValue);
            }

            // Сравнение чисел или строк соответственно
            if (type == DataType.INTEGER) {
                int a = Integer.parseInt(inputLines[i]);
                if (a < minIntValue) {  // ТУТ СРАВНИВАНИЕ
                    minElementIndex = i;
                    minIntValue = a;
                }
            } else {
                if (inputLines[i].compareTo(minStrValue) < 0) minStrValue = inputLines[i];  // ТУТ СРАВНИНВАНИЕ
                minElementIndex = i;
            }
        }

        // Задать значение текстового минимального значения, если тип сортируемых данных - числа
        if (type == DataType.INTEGER && minStrValue != null) minStrValue = String.valueOf(minIntValue);
        if (minElementIndex != -1) inputLines[minElementIndex] = null;

        return minStrValue;
    }


    public boolean mergeFiles() {
        BufferedReader[] readers = getFileReaders(settings.getInputFiles());
        if (readers == null) return false;
        // С пом. этого массива сможем проверять, достиг ли ридер EOF, не используя
        // сравнение "reader.readLine() != null" множество раз
        boolean[] stopReading = new boolean[readers.length];
        // Массив строк, ожидающих вставки в выходной файл, где
        // каждая строка соответствует своему ридеру (по индексу)
        String[] inputLines = new String[readers.length];
        // Массив последних получ. из ридеров значений для проверки правильности сортировки при чтении нового значения
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

//        printAllReaders(readers);


        closeReaders(readers);
        return true;
    }

}
