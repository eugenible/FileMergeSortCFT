package ru.evgeny;

public class InputValidator {
    private static final int minArguments = 3;

    public boolean isValid(String[] args) {
        if (args.length < minArguments) return false; // Передано недостаточное кол-во аргументов
        int currI = 0;
        if (args[currI].equals("-a") || args[currI].equals("-d")) currI++;
        if (!args[currI].equals("-s") && !args[currI].equals("-i")) return false;  // Не указан обязательный флаг
        return true;
    }

}
