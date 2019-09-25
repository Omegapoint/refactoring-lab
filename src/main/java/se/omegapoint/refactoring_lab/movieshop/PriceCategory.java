package se.omegapoint.refactoring_lab.movieshop;

public interface PriceCategory {
    double amount(int daysRented, int priceCode);
}
