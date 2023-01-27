package ru.evgeny.merge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SortingSettings {

    private Order order;
    private DataType type;
    private File outputFile;
    private boolean outputFileFound;
    private List<File> inputFiles;
    private List<String> missingInputFiles;

    public SortingSettings() {
        order = Order.ASC;
        type = DataType.INTEGER;
        outputFile = null;
        outputFileFound = false;
        inputFiles = new ArrayList<>();
        missingInputFiles = new ArrayList<>();
    }

    public static SortingSettings getInstance(String[] args) throws IOException {
        int minArguments = 3;
        if (args.length < minArguments) throw new IOException("Too few parameters");
        int currI = 0;
        SortingSettings settings = new SortingSettings();

        if (args[currI].equals("-a") || args[currI].equals("-d")) {
            if (args[currI].equals("-d")) settings.setOrder(Order.DESC);
            currI++;
        }

        if (!args[currI].equals("-s") && !args[currI].equals("-i")) {
            throw new IOException("Type of data to sort was not specified");
        } else if (args[currI].equals("-s")) {
            settings.setType(DataType.STRING);
        }

        File outputFile = new File(args[++currI]);
        settings.setOutputFileFound(!outputFile.createNewFile());
        settings.setOutputFile(outputFile);

        while (++currI < args.length) {
            File inputFile = new File(args[currI]);
            if (inputFile.isFile()) settings.addInputFile(inputFile);
            else settings.getMissingInputFiles().add(inputFile.getName());
        }

        if (settings.getInputFiles().size() == 0)
            throw new IOException("There must be at least one input file to produce output file");

        return settings;
    }

    public static void printInfo(SortingSettings settings) {
        System.out.println("Order type: " + settings.getOrder());
        System.out.println("Data type: " + settings.getType());
        System.out.println("Output file: " + settings.getOutputFile().getName());
        for (File f : settings.getInputFiles()) {
            System.out.println("Input file: " + f.getName());
        }
        System.out.println();

        if (!settings.isOutputFileFound()) {
            System.out.println(
                    "Выходной файл \"" + settings.getOutputFile().getName() + "\" был найден и был создан программой");
        }

        for (String name : settings.getMissingInputFiles()) {
            System.out.println("Входной файл \"" + name + "\" не был найден");
        }
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

    public boolean isOutputFileFound() {
        return outputFileFound;
    }

    public void setOutputFileFound(boolean outputFileFound) {
        this.outputFileFound = outputFileFound;
    }

    public List<String> getMissingInputFiles() {
        return missingInputFiles;
    }

    public void setMissingInputFiles(List<String> missingInputFiles) {
        this.missingInputFiles = missingInputFiles;
    }
}


