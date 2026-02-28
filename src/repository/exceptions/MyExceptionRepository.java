package repository.exceptions;

public class MyExceptionRepository extends RuntimeException {
    public MyExceptionRepository(String message) {
        super(message);
    }
}
