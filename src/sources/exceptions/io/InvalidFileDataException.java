package sources.exceptions.io;

public class InvalidFileDataException extends Exception {
    public InvalidFileDataException(String path, String message) {
        super("wrong data in  " + path + ": " + message + " !");
    }
}
