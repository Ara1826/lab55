package sources.client;

import sources.models.Movie;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


public class PrettyPrinter {
    static final String dateTimeFormat = "dd.MM.yy HH:mm";
    static final String birthdayTimeFormat = "dd.MM.yyyy";
    static final String keyFormat = "%5s |";
    static final String movieIDFormat = "%5s |";
    static final String movieNameFormat = "%15s |";
    static final String XFormat = "%7s |";
    static final String YFormat = "%7s |";
    static final String dateFormat = "%15s |";
    static final String oscarsFormat = "%7s |";
    static final String genreFormat = "%9s |";
    static final String ratingFormat = "%7s |";
    static final String actorNameFormat = "%20s |";
    static final String birthdayFormat = "%12s |";
    static final String weightFormat = "%10s |";
    static final String salaryFormat = "%20s |";


    static void printMovieHashMap(HashMap<Integer, Movie> hashMap) {
        System.out.printf(keyFormat, "Key");
        printMovieParamNames();
        System.out.println("-".repeat(165));

        for (Integer key : hashMap.keySet()) {
            Movie movie = hashMap.get(key);
            System.out.printf(keyFormat, key);
            printMovieParams(movie);
        }
    }


    public static void printMovieList(List<Movie> movieList) {
        printMovieParamNames();
        System.out.println("-".repeat(158));

        for (Movie movie : movieList) {
            printMovieParams(movie);
        }
    }


    public static void printMovieListOscars(List<Movie> movieList) {
        System.out.printf(movieIDFormat, "ID");
        System.out.printf(movieNameFormat, "movieName");
        System.out.printf(oscarsFormat, "oscars");
        System.out.println();
        System.out.println("-".repeat(33));

        for (Movie movie : movieList) {
            System.out.printf(movieIDFormat, movie.getID());
            System.out.printf(movieNameFormat, movie.getName());
            System.out.printf(oscarsFormat, movie.getOscarsCount());
            System.out.println();
        }
    }


    private static void printMovieParamNames() {
        System.out.printf(movieIDFormat, "ID");
        System.out.printf(movieNameFormat, "movieName");
        System.out.printf(XFormat, "coordX");
        System.out.printf(YFormat, "coordY");
        System.out.printf(dateFormat, "creationDate");
        System.out.printf(oscarsFormat, "oscars");
        System.out.printf(genreFormat, "genre");
        System.out.printf(ratingFormat, "rating");
        System.out.printf(actorNameFormat, "Name");
        System.out.printf(birthdayFormat, "Birthday");
        System.out.printf(weightFormat, "Weight");
        System.out.printf(salaryFormat, "salary");
        System.out.println();
    }

    private static void printMovieParams(Movie movie) {
        System.out.printf(movieIDFormat, movie.getID());
        System.out.printf(movieNameFormat, movie.getName());
        System.out.printf(XFormat, movie.getCoordinates().getX());
        System.out.printf(YFormat, movie.getCoordinates().getY());
        System.out.printf(dateFormat, movie.getCreationDate().format(DateTimeFormatter.ofPattern(dateTimeFormat)));
        System.out.printf(oscarsFormat, movie.getOscarsCount());
        System.out.printf(genreFormat, movie.getGenre());
        System.out.printf(ratingFormat, movie.getMpaaRating());
        System.out.printf(actorNameFormat, movie.getactor().getName());
        System.out.printf(birthdayFormat, movie.getactor().getBirthday().format(DateTimeFormatter.ofPattern(birthdayTimeFormat)));
        System.out.printf(weightFormat, movie.getactor().getWeight());
        System.out.printf(salaryFormat, movie.getactor().getsalary());
        System.out.println();
    }
}