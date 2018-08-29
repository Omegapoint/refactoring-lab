package se.omegapoint.refactoring_lab.movieshop;

public class ChildrensMovie extends Movie {
    public ChildrensMovie(String title) {
        super(title, CHILDRENS);
    }

    public double charge(int daysRented) {
        double thisAmount = 0;
        // determine amount for each line
        switch (getPriceCode()) {
            case REGULAR:
                thisAmount += 2;
                if (daysRented > 2)
                    thisAmount += (daysRented - 2) * 1.5;
                break;
            case NEW_RELEASE:
                thisAmount += daysRented * 3;
                break;
            case CHILDRENS:
                thisAmount += 1.5;
                if(daysRented > 3)
                    thisAmount += (daysRented - 3) * 1.5;
                break;
        }
        return thisAmount;
    }
}
