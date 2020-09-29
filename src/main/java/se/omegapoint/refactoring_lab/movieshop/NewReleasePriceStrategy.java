package se.omegapoint.refactoring_lab.movieshop;

public class NewReleasePriceStrategy implements PriceStrategy {
    @Override
    public double amount(int daysRented) {
        double tmpAmount = 0;
        tmpAmount += daysRented * 3;
        return tmpAmount;
    }
}
