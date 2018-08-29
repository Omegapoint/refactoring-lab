package se.omegapoint.refactoring_lab.movieshop;

public class NewRelaseMovie extends Movie {
    public NewRelaseMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return NEW_RELEASE;
    }

    public double charge(int daysRented) {
        return daysRented * 3;
    }
}
