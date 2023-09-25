package sources.receiver;

import sources.IOHandlers.receiver.MovieCollectionFileReader;
import sources.IOHandlers.receiver.MovieCollectionFileWriter;
import sources.IOHandlers.receiver.MovieCollectionXMLFileReader;
import sources.IOHandlers.receiver.MovieCollectionXMLFileWriter;
import sources.exceptions.io.CustomIOException;
import sources.exceptions.io.FilePermissionException;
import sources.exceptions.io.InvalidFileDataException;
import sources.exceptions.io.WrongArgumentException;
import sources.exceptions.receiver.CollectionKeyException;
import sources.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class Receiver {
    private final MovieCollection movieCollection;
    MovieCollectionFileReader xmlFileReader;
    MovieCollectionFileWriter xmlFileWriter;

    public Receiver() throws InvalidFileDataException, FileNotFoundException, FilePermissionException {
        String path = System.getenv("laba5");
        checkFile(path);

        this.xmlFileReader = new MovieCollectionXMLFileReader(path);
        this.xmlFileWriter = new MovieCollectionXMLFileWriter(path);

        this.movieCollection = xmlFileReader.read();
    }

    public String info() {
        return "Information about collection\n" +
                "- Type of collection   : Hashmap of Movies\n" +
                "- Date of initializing : " + movieCollection.getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "\n" +
                "- Number of elements   : " + movieCollection.length();
    }
    public HashMap<Integer, Movie> show() {
        return movieCollection.getMovieHashMap();
    }



    public void insert(Integer key, String movieName, Integer x, Integer y, long oscarsCount, MovieGenre movieGenre,
                       MpaaRating mpaaRating, String actorName, LocalDateTime birthday, Integer weight,
                       String salary) throws CollectionKeyException, WrongArgumentException {
        if (movieCollection.getElementByKey(key) != null)
            throw new CollectionKeyException("key already existF");
        Movie movie = new Movie(movieName, new Coordinates(x, y), oscarsCount, movieGenre,
                mpaaRating, new Person(actorName, birthday, weight, salary));
        movie.setID();
        movieCollection.put(key, movie);
        System.out.println("Element is saved");
    }

    public void update(Integer id, String movieName, Integer x, Integer y, long oscarsCount, MovieGenre movieGenre,
                       MpaaRating mpaaRating, String actorName, LocalDateTime birthday, Integer weight,
                       String salary) throws CollectionKeyException, WrongArgumentException {
        Movie movie = movieCollection.getElementByID(id);
        if (movie == null)
            throw new CollectionKeyException("не существует заданного id");
        movie.setName(movieName);
        movie.setCoordinates(new Coordinates(x, y));
        movie.setOscarsCount(oscarsCount);
        movie.setGenre(movieGenre);
        movie.setMpaaRating(mpaaRating);
        movie.setactor(new Person(actorName, birthday, weight, salary));
        System.out.println("Элемент успешно добавлен");
    }


    public void removeKey(Integer key) throws CollectionKeyException {
        if (movieCollection.getElementByKey(key) == null)
            throw new CollectionKeyException("такого ключа не существует");
        movieCollection.remove(key);
        System.out.println("Элемент успешно удален");
    }


    public void clear() {
        movieCollection.clear();
        System.out.println("коллекция очищена");
    }


    public void save() {
        try {
            xmlFileWriter.write(movieCollection);
            System.out.println("коллекция сохранилась");
        } catch (FileNotFoundException | FilePermissionException | CustomIOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void removeGreater(String movieName, Integer x, Integer y, long oscarsCount, MovieGenre movieGenre,
                              MpaaRating mpaaRating, String actorName, LocalDateTime birthday, Integer weight,
                              String salary) throws WrongArgumentException {
        Movie movie = new Movie(movieName, new Coordinates(x, y), oscarsCount, movieGenre,
                mpaaRating, new Person(actorName, birthday, weight, salary));
        int count = movieCollection.removeGreater(movie);
        if (count == 0) {
            System.out.println("*no elements removed*");
        } else {
            System.out.println("* " + count + " elements removed successfully*");
        }
    }


    public void replaceIfLowe(Integer key, String movieName, Integer x, Integer y, long oscarsCount,
                              MovieGenre movieGenre, MpaaRating mpaaRating, String actorName, LocalDateTime birthday,
                              Integer weight, String salary) throws CollectionKeyException, WrongArgumentException {
        if (movieCollection.getElementByKey(key) == null)
            throw new CollectionKeyException("такого ключа не существует");
        Movie movie = new Movie(movieName, new Coordinates(x, y), oscarsCount, movieGenre,
                mpaaRating, new Person(actorName, birthday, weight, salary));
        boolean replaced = movieCollection.replaceIfLowe(key, movie);
        if (replaced) {
            movie.setID();
            System.out.println("*element replaced successfully*");
        } else {
            System.out.println("*element was not replaced*");
        }
    }


    public void removeLowerKey(Integer key) {
        int count = movieCollection.removeLowerKey(key);
        if (count == 0) {
            System.out.println("*no elements removed*");
        } else {
            System.out.println("* " + count + " elements removed successfully*");
        }
    }


    public List<Movie> printAscending() {
        return movieCollection.printAscending();
    }


    public List<Movie> printDescending() {
        return movieCollection.printDescending();
    }


    public List<Movie> printFieldDescendingOscarsCount() {
        return movieCollection.printFieldDescendingOscarsCount();
    }


    private void checkFile(String path) throws FileNotFoundException, FilePermissionException {
        File file = new File(path);
        if (!file.exists())
            throw new FileNotFoundException("! file " + path + " not found !");
        if (!file.canRead())
            throw new FilePermissionException("! no read and/or write permission for file " + path + "  !");
    }
}
