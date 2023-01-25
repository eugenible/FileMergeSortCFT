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


    // мб и не надо булин массив, раз могут быт наллы для объектов входных?
    public void fillInputLines(BufferedReader[] readers, boolean[] readerAtEOF, Object[] inputLines) throws IOException {
        for (int i = 0; i < inputLines.length; ++i) {
            if (inputLines[i] != null) continue;
            String line = null;
            boolean isValidLine = false;
            while (!readerAtEOF[i] && !isValidLine) {
                if ((line = readers[i].readLine()) == null) {
                    readerAtEOF[i] = true;
                    continue;
                }

                inputLines[i] = line;

            }
        }
    }

    public Object chooseLine(BufferedReader[] readers, boolean[] readerAtEOF, Object[] inputLines) throws IOException {
        fillInputLines(readers, readerAtEOF, inputLines);  // Заполнили массив для дальнейшего выбора миним. значения
        // Выставить в null наименьший элемент

    }


    public boolean mergeFiles() {
        BufferedReader[] readers = getFileReaders(settings.getInputFiles());
        if (readers == null) return false;
        // С пом. этого массива сможем проверять, достиг ли ридер EOF, не используя
        // сравнение "reader.readLine() != null" множество раз
        boolean[] readerAtEOF = new boolean[readers.length];
        Object[] inputLines = new Object[readers.length];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(settings.getOutputFile()))) {

            while (true) {
                Object lineToWrite = chooseLine(readers, readerAtEOF, inputLines);
                if (lineToWrite == null) break;
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
