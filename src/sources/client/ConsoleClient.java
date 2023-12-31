package sources.client;

import sources.IOHandlers.client.BasicReader;
import sources.IOHandlers.client.CustomConsoleReader;
import sources.IOHandlers.client.CustomFileReader;
import sources.commands.*;
import sources.exceptions.client.FileRecursionError;
import sources.exceptions.client.InvalidCommandException;
import sources.exceptions.client.InvalidScriptException;
import sources.exceptions.client.WrongNumberOfArgumentsException;
import sources.exceptions.io.*;
import sources.exceptions.receiver.CollectionKeyException;
import sources.models.Movie;
import sources.models.MovieGenre;
import sources.models.MpaaRating;
import sources.models.helpers.MovieArgumentChecker;
import sources.receiver.Receiver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import static sources.client.MovieDataConsoleReader.*;


public class ConsoleClient implements Client {
    Invoker invoker;
    Receiver receiver;
    private final Stack<String> pathStack = new Stack<>();
    private boolean canExit = false;

    public void main() {
        try {
            invoker = new Invoker();
            receiver = new Receiver();
            BasicReader consoleReader = new CustomConsoleReader();

            System.out.println("Data is loaded. Insert 'help' for print commands \n");

            while (!canExit) {
                try {
                    readAndExecuteCommand(consoleReader);
                } catch (InvalidCommandException | CollectionKeyException | WrongNumberOfArgumentsException |
                         WrongArgumentException | InvalidScriptException | CustomIOException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("! переменная path пуста !");
            System.exit(0);
        } catch (InvalidFileDataException | EndOfInputException | FilePermissionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    private void readAndExecuteCommand(BasicReader basicReader) throws InvalidCommandException, CollectionKeyException,
            WrongNumberOfArgumentsException, WrongArgumentException, InvalidScriptException, CustomIOException {
        String input = basicReader.readLine().trim();
        if (input.startsWith("//") || input.equals("")) {
            return;
        }
        String[] inputArray = input.split("\s+");
        String commandName = inputArray[0].toLowerCase();

        String[] args = new String[inputArray.length - 1];
        System.arraycopy(inputArray, 1, args, 0, inputArray.length - 1);

        switch (commandName) {
            case "help" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                invoker.execute(new Help(this, receiver));
            }
            case "history" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                invoker.execute(new History(this, receiver));
            }
            case "info" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                String result = invoker.executeAndReturn(new Info(this, receiver));
                System.out.println(result);
            }
            case "show" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                HashMap<Integer, Movie> result = invoker.executeAndReturn(new Show(this, receiver));
                PrettyPrinter.printMovieHashMap(result);
            }
            case "insert" -> {
                if (args.length != 1)
                    throw new WrongNumberOfArgumentsException();
                try {
                    boolean inScriptMode = inScriptMode();
                    Integer key = Integer.parseInt(args[0]);
                    MovieArgumentChecker.checkKey(key);
                    String movieName = readMovieName(basicReader, inScriptMode);
                    Integer x = readX(basicReader, inScriptMode);
                    Integer y = readY(basicReader, inScriptMode);
                    long oscarsCount = readOscrasCount(basicReader, inScriptMode);
                    MovieGenre movieGenre = readMovieGenre(basicReader, inScriptMode);
                    MpaaRating mpaaRating = readMpaaRating(basicReader, inScriptMode);
                    String actorName = readactorName(basicReader, inScriptMode);
                    LocalDateTime birthday = readBirthday(basicReader, inScriptMode);
                    Integer weight = readWeight(basicReader, inScriptMode);
                    String salary = readsalary(basicReader, inScriptMode);
                    invoker.execute(new Insert(this, receiver, key, movieName, x, y, oscarsCount,
                            movieGenre, mpaaRating, actorName, birthday, weight, salary));
                } catch (NumberFormatException e) {
                    String errorMessage = "! not an integer !";
                    if (inScriptMode()) {
                        throw new InvalidScriptException(errorMessage);
                    } else {
                        System.out.println(errorMessage);
                    }
                }
            }
            case "update" -> {
                if (args.length != 1)
                    throw new WrongNumberOfArgumentsException();
                try {
                    boolean inScriptMode = inScriptMode();
                    Integer id = Integer.parseInt(args[0]);
                    String movieName = readMovieName(basicReader, inScriptMode);
                    Integer x = readX(basicReader, inScriptMode);
                    Integer y = readY(basicReader, inScriptMode);
                    long oscarsCount = readOscrasCount(basicReader, inScriptMode);
                    MovieGenre movieGenre = readMovieGenre(basicReader, inScriptMode);
                    MpaaRating mpaaRating = readMpaaRating(basicReader, inScriptMode);
                    String actorName = readactorName(basicReader, inScriptMode);
                    LocalDateTime birthday = readBirthday(basicReader, inScriptMode);
                    Integer weight = readWeight(basicReader, inScriptMode);
                    String salary = readsalary(basicReader, inScriptMode);
                    invoker.execute(new Update(this, receiver, id, movieName, x, y, oscarsCount,
                            movieGenre, mpaaRating, actorName, birthday, weight, salary));
                } catch (NumberFormatException e) {
                    String errorMessage = "! not an integer !";
                    if (inScriptMode()) {
                        throw new InvalidScriptException(errorMessage);
                    } else {
                        System.out.println(errorMessage);
                    }
                }
            }
            case "remove_key" -> {
                if (args.length != 1)
                    throw new WrongNumberOfArgumentsException();
                try {
                    Integer key = Integer.parseInt(args[0]);
                    MovieArgumentChecker.checkKey(key);
                    invoker.execute(new RemoveKey(this, receiver, key));
                } catch (NumberFormatException e) {
                    String errorMessage = "! not an integer !";
                    if (inScriptMode()) {
                        throw new InvalidScriptException(errorMessage);
                    } else {
                        System.out.println(errorMessage);
                    }
                }
            }
            case "clear" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                invoker.execute(new Clear(this, receiver));
            }
            case "save" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                invoker.execute(new Save(this, receiver));
            }
            case "execute_script" -> {
                if (args.length != 1)
                    throw new WrongNumberOfArgumentsException();
                String path = args[0];
                invoker.execute(new ExecuteScript(this, receiver, path));
            }
            case "exit" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                invoker.execute(new Exit(this, receiver));
            }
            case "remove_greater" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                boolean inScriptMode = inScriptMode();
                String movieName = readMovieName(basicReader, inScriptMode);
                Integer x = readX(basicReader, inScriptMode);
                Integer y = readY(basicReader, inScriptMode);
                long oscarsCount = readOscrasCount(basicReader, inScriptMode);
                MovieGenre movieGenre = readMovieGenre(basicReader, inScriptMode);
                MpaaRating mpaaRating = readMpaaRating(basicReader, inScriptMode);
                String actorName = readactorName(basicReader, inScriptMode);
                LocalDateTime birthday = readBirthday(basicReader, inScriptMode);
                Integer weight = readWeight(basicReader, inScriptMode);
                String salary = readsalary(basicReader, inScriptMode);
                invoker.execute(new RemoveGreater(this, receiver, movieName, x, y,
                        oscarsCount, movieGenre, mpaaRating, actorName, birthday, weight, salary));
            }
            case "replace_if_lowe" -> {
                if (args.length != 1)
                    throw new WrongNumberOfArgumentsException();
                try {
                    boolean inScriptMode = inScriptMode();
                    Integer key = Integer.parseInt(args[0]);
                    MovieArgumentChecker.checkKey(key);
                    String movieName = readMovieName(basicReader, inScriptMode);
                    Integer x = readX(basicReader, inScriptMode);
                    Integer y = readY(basicReader, inScriptMode);
                    long oscarsCount = readOscrasCount(basicReader, inScriptMode);
                    MovieGenre movieGenre = readMovieGenre(basicReader, inScriptMode);
                    MpaaRating mpaaRating = readMpaaRating(basicReader, inScriptMode);
                    String actorName = readactorName(basicReader, inScriptMode);
                    LocalDateTime birthday = readBirthday(basicReader, inScriptMode);
                    Integer weight = readWeight(basicReader, inScriptMode);
                    String salary = readsalary(basicReader, inScriptMode);
                    invoker.execute(new ReplaceIfLowe(this, receiver, key, movieName, x, y,
                            oscarsCount, movieGenre, mpaaRating, actorName, birthday, weight, salary));
                } catch (NumberFormatException e) {
                    String errorMessage = "! not an integer !";
                    if (inScriptMode()) {
                        throw new InvalidScriptException(errorMessage);
                    } else {
                        System.out.println(errorMessage);
                    }
                }
            }
            case "remove_lower_key" -> {
                if (args.length != 1)
                    throw new WrongNumberOfArgumentsException();
                try {
                    Integer key = Integer.parseInt(args[0]);
                    MovieArgumentChecker.checkKey(key);
                    invoker.execute(new RemoveLowerKey(this, receiver, key));
                } catch (NumberFormatException e) {
                    String errorMessage = "! not an integer !";
                    if (inScriptMode()) {
                        throw new InvalidScriptException(errorMessage);
                    } else {
                        System.out.println(errorMessage);
                    }
                }
            }
            case "print_ascending" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                List<Movie> movieList = invoker.executeAndReturn(new PrintAscending(this, receiver));
                System.out.println("*elements of collection ascended*");
                PrettyPrinter.printMovieList(movieList);
            }
            case "print_descending" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                List<Movie> movieList = invoker.executeAndReturn(new PrintDescending(this, receiver));
                System.out.println("*elements of collection descended*");
                PrettyPrinter.printMovieList(movieList);
            }
            case "print_field_descending_oscars_count" -> {
                if (args.length != 0)
                    throw new WrongNumberOfArgumentsException();
                List<Movie> movieList = invoker.executeAndReturn(new PrintFieldDescendingOscarsCount(this, receiver));
                System.out.println("*oscars count descended*");
                PrettyPrinter.printMovieListOscars(movieList);
            }

            default -> throw new InvalidCommandException(commandName);
        }
    }


    @Override
    public void help() {
        System.out.println("List of commands");
        System.out.printf("%-37s", "- help");
        System.out.println(" : display a list of available commands");
        System.out.printf("%-37s", "- info");
        System.out.println(" : display information about the collection (type, initialization date, number of elements, etc.) in the standard output stream");
        System.out.printf("%-37s", "- show");
        System.out.println(" : display all elements of the collection in string representation in the standard output stream");
        System.out.printf("%-37s", "- insert null {element}");
        System.out.println(" : add a new element with a specified key");
        System.out.printf("%-37s", "- update id {element}");
        System.out.println(" : update the values of collection elements with an id equal to the specified one");
        System.out.printf("%-37s", "- remove_key null");
        System.out.println(" : remove an element from the collection by its key");
        System.out.printf("%-37s", "- clear");
        System.out.println(" : clear the collection");
        System.out.printf("%-37s", "- save");
        System.out.println(" : save the collection to a file");
        System.out.printf("%-37s", "- execute_script file_name");
        System.out.println(" : read and execute a script from the specified file. The script contains commands in the same format as the user enters them in interactive mode.");
        System.out.printf("%-37s", "- exit");
        System.out.println(" : exit the program (without saving to a file)");
        System.out.printf("%-37s", "- remove_greater {element}");
        System.out.println(" : remove from the collection all elements greater than the specified one");
        System.out.printf("%-37s", "- replace_if_lower null {element}");
        System.out.println(" : replace the value by key if the new value is lower than the old one");
        System.out.printf("%-37s", "- remove_lower_key null");
        System.out.println(" : remove from the collection all elements whose key is less than the specified one");
        System.out.printf("%-37s", "- print_ascending");
        System.out.println(" : display the elements of the collection in ascending order");
        System.out.printf("%-37s", "- print_descending");
        System.out.println(" : display the elements of the collection in descending order");
        System.out.printf("%-37s", "- print_field_descending_oscars_count");
        System.out.println(" : display the values of the oscarsCount field of all elements in descending order");
    }


    @Override
    public void exit() {
        canExit = true;
    }


    @Override
    public void history() {
        Stack<AbstractCommand> commandHistory = invoker.getCommandHistory();
        System.out.println("*command history*");
        for (AbstractCommand command : commandHistory) {
            System.out.println(command);
        }
    }

    @Override
    public void executeScript(String path) throws CustomIOException {
        try {
            if (pathStackContains(path))
                throw new FileRecursionError(path);

            BasicReader basicReader = new CustomFileReader(path);
            pathStack.push(path);
            int lineCounter = 0;
            while (basicReader.hasNextLine()) {
                try {
                    lineCounter += 1;
                    readAndExecuteCommand(basicReader);
                } catch (InvalidCommandException | CollectionKeyException | WrongNumberOfArgumentsException |
                         WrongArgumentException | InvalidScriptException e) {
                    System.out.println(printPathStack() + ":" + lineCounter + ": " + e.getMessage());
                }
            }
            pathStack.pop();
        } catch (FileRecursionError | FileNotFoundException | FilePermissionException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean pathStackContains(String pathToCheck) throws CustomIOException {
        try {
            for (String pathFromStack : pathStack) {
                Path path1 = Paths.get(pathFromStack);
                Path path2 = Paths.get(pathToCheck);
                if (Files.isSameFile(path1, path2))
                    return true;
            }
            return false;
        } catch (IOException e) {
            throw new CustomIOException(e.getMessage());
        }
    }

    private String printPathStack() {
        StringBuilder returnString = new StringBuilder();
        for (String path : pathStack) {
            returnString.append(path).append(":");
        }
        returnString.deleteCharAt(returnString.length() - 1);
        return returnString.toString();
    }


    private boolean inScriptMode() {
        return !pathStack.empty();
    }
}
