package sources.IOHandlers.client;

import sources.exceptions.io.FilePermissionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class CustomFileReader implements BasicReader {
    String path;
    Scanner scanner;

    public CustomFileReader(String path) throws FileNotFoundException, FilePermissionException {
        File file = new File(path);
        if (!file.exists())
            throw new FileNotFoundException(" файл " + path + " не найден ");
        if (!file.canRead() || !file.canWrite())
            throw new FilePermissionException("! no read and/or write permission for file " + path + "  !");

        this.path = path;
        this.scanner = new Scanner(new BufferedReader(new FileReader(path)));
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public String readLine(String message) {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}