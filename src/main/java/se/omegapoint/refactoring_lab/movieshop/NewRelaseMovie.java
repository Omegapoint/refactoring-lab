package se.omegapoint.refactoring_lab.movieshop;

public class NewRelaseMovie extends Movie {
    public NewRelaseMovie(String title) {
        super(title, Movie.NEW_RELEASE);
    }

    public double charge(int daysRented) {
        return daysRented * 3;
    }
}
