package se.omegapoint.refactoring_lab.movieshop;

public class RegularPriceCategory implements PriceCategory {
    public RegularPriceCategory() {
    }

    @Override
    public double amount(int daysRented, int priceCode) {
        switch (priceCode) {
            case Movie.REGULAR: {
                if (daysRented > 2)
                    return 2 + (daysRented - 2) * 1.5;
                else
                    return 2;
            }
            default:
                throw new IllegalArgumentException();
        }
    }
}