package sources.receiver;

import sources.models.Movie;

import java.time.ZonedDateTime;
import java.util.*;


public class MovieCollection {
    private HashMap<Integer, Movie> movieHashMap = new HashMap<>();
    private java.time.ZonedDateTime creationDate;


    public MovieCollection() {
        this.creationDate = ZonedDateTime.now();
    }


    public void put(Integer key, Movie movie) {
        movieHashMap.put(key, movie);
    }


    public Movie getElementByKey(Integer key) {
        return movieHashMap.get(key);
    }


    public Movie getElementByID(Integer id) {
        for (Movie movie : movieHashMap.values()) {
            if (Objects.equals(movie.getID(), id))
                return movie;
        }
        return null;
    }


    public void remove(Integer key) {
        movieHashMap.remove(key);
    }


    public void clear() {
        movieHashMap.clear();
    }


    public HashMap<Integer, Movie> getMovieHashMap() {
        return movieHashMap;
    }


    public int length() {
        return movieHashMap.size();
    }


    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int removeGreater(Movie movie) {
        HashMap<Integer, Movie> newMovieHashMap = new HashMap<>(movieHashMap);
        int count = 0;
        for (Integer key : movieHashMap.keySet()) {
            if (getElementByKey(key).compareTo(movie) > 0) {
                newMovieHashMap.remove(key);
                count += 1;
            }
        }
        movieHashMap = newMovieHashMap;
        return count;
    }


    public boolean replaceIfLowe(Integer key, Movie movie) {
        if (getElementByKey(key).compareTo(movie) > 0) {
            put(key, movie);
            return true;
        }
        return false;
    }


    public int removeLowerKey(Integer key) {
        HashMap<Integer, Movie> newMovieHashMap = new HashMap<>(movieHashMap);
        int count = 0;
        for (Integer i : movieHashMap.keySet()) {
            if (i < key) {
                newMovieHashMap.remove(i);
                count += 1;
            }
        }
        movieHashMap = newMovieHashMap;
        return count;
    }


    public List<Movie> printAscending() {
        List<Movie> movieList = new ArrayList<>(movieHashMap.values());
        Collections.sort(movieList);
        return movieList;
    }


    public List<Movie> printDescending() {
        List<Movie> movieList = new ArrayList<>(movieHashMap.values());
        Collections.sort(movieList);
        Collections.reverse(movieList);
        return movieList;
    }


    public List<Movie> printFieldDescendingOscarsCount() {
        List<Movie> movieList = new ArrayList<>(movieHashMap.values());
        movieList.sort(new Comparator<>() {
            public int compare(Movie o1, Movie o2) {
                return (int) (o2.getOscarsCount() - o1.getOscarsCount());
            }
        });
        return movieList;
    }
}
