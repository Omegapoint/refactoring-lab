package se.omegapoint.refactoring_lab.movieshop;

public class Rental {
    Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    int frequentRenterPoints() {
        // add bonus for two days new release rental
        if ((movie.getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1)
            return 2;
        else
            return 1;
    }

    String record() {
        return "\t" + movie.getTitle() + "\t" + String.valueOf(movie.amount(this.getDaysRented())) + "\n";
    }
}
