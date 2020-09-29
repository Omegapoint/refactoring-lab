package se.omegapoint.refactoring_lab.movieshop;

public class ChildrensPriceStrategy implements PriceStrategy {
    @Override
    public double amount(int daysRented) {
        double tmpAmount = 0;
        tmpAmount += 1.5;
        if(daysRented > 3)
            tmpAmount += (daysRented - 3) * 1.5;
        return tmpAmount;
    }
}
