package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public final class MovieInput implements Output {
    private String name;
    private String year;
    private Integer duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private Integer numLikes = 0;
    private Double rating = 0.0;
    private Integer numRatings = 0;
    private Map<String, Double> ratings = new HashMap<>();


    public MovieInput(final String name,
                      final String year,
                      final Integer duration,
                      final ArrayList<String> genres,
                      final ArrayList<String> actors,
                      final ArrayList<String> countriesBanned) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
        this.countriesBanned = countriesBanned;
    }

    public MovieInput() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final Integer numLikes) {
        this.numLikes = numLikes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final Integer numRatings) {
        this.numRatings = numRatings;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    /**
     * Generates a list with all the movies that this user can watch.
     * @param user the given user
     * @param moviesList a list with multiple movies
     * @return the movies that the user can watch based on its country
     */
    public static List<MovieInput> getUserMovies(final UserInput user,
                                                 final List<MovieInput> moviesList) {
        if (user == null) {
            return new ArrayList<>();
        }
        List<MovieInput> allowedMovies = new ArrayList<>();

        for (MovieInput movie: moviesList) {
            if (movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                continue;
            }
            allowedMovies.add(movie);
        }

        return allowedMovies;
    }

    /**
     * Convert the object to an ObjectNode for implementing the output.
     * @return  an ObjectNode with all the object's data
     */
    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("name", name);
        obj.put("duration", duration);
        ArrayNode arr = new ObjectMapper().createArrayNode();

        for (String actor: actors) {
            arr.add(actor);
        }
        obj.put("actors", arr);
        arr = new ObjectMapper().createArrayNode();

        for (String genre: genres) {
            arr.add(genre);
        }
        obj.put("genres", arr);

        arr = new ObjectMapper().createArrayNode();
        for (String country: countriesBanned) {
            arr.add(country);
        }
        obj.put("countriesBanned", arr);

        obj.put("numLikes", numLikes);
        obj.put("rating", rating);
        obj.put("numRatings", numRatings);
        obj.put("year", year);

        return obj;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieInput that = (MovieInput) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
