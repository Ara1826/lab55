package sources.models.helpers;

import sources.exceptions.io.WrongArgumentException;
import sources.models.Coordinates;
import sources.models.MovieGenre;
import sources.models.MpaaRating;
import sources.models.Person;

import java.util.Objects;

public class MovieArgumentChecker extends ArgumentChecker {
    public static void checkArguments(String name, Coordinates coordinates, long oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person actor) throws WrongArgumentException {
        MovieArgumentChecker.checkName(name);
        MovieArgumentChecker.checkCoordinates(coordinates);
        MovieArgumentChecker.checkOscarsCount(oscarsCount);
        MovieArgumentChecker.checkGenre(genre);
        MovieArgumentChecker.checkMpaaRating(mpaaRating);
        MovieArgumentChecker.checkactor(actor);
    }

    public static void checkArguments(Integer id, String name, Coordinates coordinates, long oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person actor) throws WrongArgumentException {
        MovieArgumentChecker.checkID(id);
        MovieArgumentChecker.checkName(name);
        MovieArgumentChecker.checkCoordinates(coordinates);
        MovieArgumentChecker.checkOscarsCount(oscarsCount);
        MovieArgumentChecker.checkGenre(genre);
        MovieArgumentChecker.checkMpaaRating(mpaaRating);
        MovieArgumentChecker.checkactor(actor);
    }

    public static void checkID(Integer id) throws WrongArgumentException {
        checkNull(id, "id");
        checkArgument(id > 0, "argument can't be <= 0");
    }

    public static void checkKey(Integer key) throws WrongArgumentException {
        checkNull(key, "key");
        checkArgument(key > 0, "argument can't be <= 0");
    }

    public static void checkName(String name) throws WrongArgumentException {
        checkNull(name, "name");
        checkArgument(!Objects.equals(name, ""), "argument can't be null");
    }

    public static void checkCoordinates(Coordinates coordinates) throws WrongArgumentException {
        checkNull(coordinates, "coordinates");
    }

    public static void checkOscarsCount(long oscarsCount) throws WrongArgumentException {
        checkArgument(oscarsCount > 0, "argument can't be <= 0");
    }

    public static void checkGenre(MovieGenre genre) throws WrongArgumentException {
        checkNull(genre, "genre");
    }

    public static void checkMpaaRating(MpaaRating mpaaRating) throws WrongArgumentException {
        checkNull(mpaaRating, "mpaaRating");
    }

    public static void checkactor(Person actor) throws WrongArgumentException {
        checkNull(actor, "actor");
    }
}
