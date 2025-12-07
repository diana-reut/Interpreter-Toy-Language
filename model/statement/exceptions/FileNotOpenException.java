package model.statement.exceptions;

public class FileNotOpenException extends RuntimeException {
    public FileNotOpenException(String message) {
        super(message);
    }
}
