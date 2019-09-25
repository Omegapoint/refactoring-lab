package se.omegapoint.refactoring_lab.movieshop;

public class NewReleasePriceCategory implements PriceCategory {
    public NewReleasePriceCategory() {
    }

    @Override
    public double amount(int daysRented) {
        return daysRented * 3;
    }
}