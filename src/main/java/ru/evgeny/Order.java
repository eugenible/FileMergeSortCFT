package ru.evgeny;

public enum Order {
    ASC,
    DESC;

    public Order opposite() {
        return switch (this) {
            case ASC -> DESC;
            case DESC -> ASC;
        };
    }
}
