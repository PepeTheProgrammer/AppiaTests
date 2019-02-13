package reusableElements.tableFilesHandlers.tableExceptions;

public class NoSuchColumnException extends Exception{

    public NoSuchColumnException(String message) {
        super(message);
    }

    public NoSuchColumnException() {
    }
}
