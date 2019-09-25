package se.omegapoint.refactoring_lab.movieshop;

public class RegularPriceCategory implements PriceCategory {
    public RegularPriceCategory() {
    }

    @Override
    public double amount(int daysRented) {
        if (daysRented > 2) {
            return 2 + (daysRented - 2) * 1.5;
        } else
            return 2;
    }
}