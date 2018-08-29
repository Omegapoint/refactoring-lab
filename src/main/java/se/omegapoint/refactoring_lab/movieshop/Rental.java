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

    public double charge() {
        double thisAmount = 0;
        // determine amount for each line
        switch (getMovie().getPriceCode()) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (getDaysRented() > 2)
                    thisAmount += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if(getDaysRented() > 3)
                    thisAmount += (getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }

    int frequentRenterPoints() {
        int thisFrequentRenterPoints = 1;
        // add bonus for two days new release rental
        if((getMovie().getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1)
            thisFrequentRenterPoints++;
        return thisFrequentRenterPoints;
    }
}
