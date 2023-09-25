package sources.exceptions.client;


public class FileRecursionError extends Exception {
    public FileRecursionError(String path) {
        super(" recursion: " + path + " called recursion !");
    }
}
