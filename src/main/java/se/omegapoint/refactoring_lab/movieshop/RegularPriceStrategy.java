package se.omegapoint.refactoring_lab.movieshop;

public class RegularPriceStrategy implements PriceStrategy {
    @Override
    public double amount(int daysRented) {
        double tmpAmount = 0;
        tmpAmount += 2;
        if (daysRented > 2)
            tmpAmount += (daysRented - 2) * 1.5;
        return tmpAmount;
    }
}
