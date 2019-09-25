package se.omegapoint.refactoring_lab.movieshop;

public class ChildrensPriceCategory implements PriceCategory {
    public ChildrensPriceCategory() {
    }

    @Override
    public double amount(int daysRented) {
        if (daysRented > 3) {
            return 1.5 + (daysRented - 3) * 1.5;
        } else
            return 1.5;
    }
}