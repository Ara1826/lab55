package sources.exceptions.client;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String command) {
        super(" wrong command: " + command + " !");
    }
}
