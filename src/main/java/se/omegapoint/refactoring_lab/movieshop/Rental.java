package se.omegapoint.refactoring_lab.movieshop;

public class Rental {
    private Movie movie;
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

    double amount() {
        // determine amount for each line
        switch (movie.getPriceCode()) {
            case Movie.REGULAR: {
                if (getDaysRented() > 2)
                    return 2 + (getDaysRented() - 2) * 1.5;
                else
                    return 2;
            }
            case Movie.NEW_RELEASE: {
                return getDaysRented() * 3;
            }
            case Movie.CHILDRENS: {
                if (getDaysRented() > 3)
                    return 1.5 + (getDaysRented() - 3) * 1.5;
                else
                    return 1.5;
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    int frequentRenterPoints() {
        // add bonus for two days new release rental
        if ((movie.getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1)
            return 2;
        else
            return 1;
    }

    String record() {
        return "\t" + movie.getTitle() + "\t" + String.valueOf(amount()) + "\n";
    }
}
