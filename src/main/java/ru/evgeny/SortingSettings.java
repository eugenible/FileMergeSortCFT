package ru.evgeny;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum Order {
    ASC,
    DESC
}


enum DataType {
    STRING,
    INTEGER
}

public class SortingSettings {

    private Order order;
    private DataType type;
    private File outputFile;
    private List<File> inputFiles = new ArrayList<>();

    public SortingSettings() {
        order = Order.ASC;
        type = DataType.INTEGER;
    }

    public static SortingSettings getInstance(String[] args) throws InvalidLineArgumentException, IOException {
        int minArguments = 3;
        if (args.length < minArguments) throw new InvalidLineArgumentException("Too few parameters");
        int currI = 0;
        SortingSettings settings = new SortingSettings();

        if (args[currI].equals("-a") || args[currI].equals("-d")) {
            if (args[currI].equals("-d")) settings.setOrder(Order.DESC);
            currI++;
        }

        if (!args[currI].equals("-s") && !args[currI].equals("-i")) {
            throw new InvalidLineArgumentException("Type of data to sort was not specified");
        } else if (args[currI].equals("-s")) {
            settings.setType(DataType.STRING);
        }

        File outputFile = new File(args[++currI]);
        if (!outputFile.isFile()) outputFile.createNewFile();
        settings.setOutputFile(outputFile);

        while (++currI < args.length) {
            File inputFile = new File(args[currI]);
            if (inputFile.isFile()) settings.addInputFile(inputFile);
        }

        // Проверка, что есть хотя бы 1 входной файл
        if (settings.getInputFiles().size() == 0)
            throw new InvalidLineArgumentException("There must be at least one input file to produce output file");

        return settings;
    }

    public void addInputFile(File file) {
        inputFiles.add(file);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public List<File> getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(List<File> inputFiles) {
        this.inputFiles = inputFiles;
    }
}


