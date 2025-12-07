package model.state;

public class AlreadyDefinedException extends RuntimeException {
    public AlreadyDefinedException(String message) {
        super(message);
    }
}
