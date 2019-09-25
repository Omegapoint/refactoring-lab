package se.omegapoint.refactoring_lab.movieshop;

public class NewReleasePriceCategory implements PriceCategory {
    public NewReleasePriceCategory() {
    }

    @Override
    public double amount(int daysRented, int priceCode) {
        switch (priceCode) {
            case Movie.NEW_RELEASE: {
                return daysRented * 3;
            }
            default:
                throw new IllegalArgumentException();
        }
    }
}