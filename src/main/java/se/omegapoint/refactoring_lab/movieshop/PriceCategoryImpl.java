package se.omegapoint.refactoring_lab.movieshop;

public class PriceCategoryImpl {
    public PriceCategoryImpl() {
    }

    double amount(int daysRented, int priceCode) {
        switch (priceCode) {
            case Movie.REGULAR: {
                if (daysRented > 2)
                    return 2 + (daysRented - 2) * 1.5;
                else
                    return 2;
            }
            case Movie.NEW_RELEASE: {
                return daysRented * 3;
            }
            case Movie.CHILDRENS: {
                if (daysRented > 3)
                    return 1.5 + (daysRented - 3) * 1.5;
                else
                    return 1.5;
            }
            default:
                throw new IllegalArgumentException();
        }
    }
}