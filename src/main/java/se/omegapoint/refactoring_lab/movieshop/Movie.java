package se.omegapoint.refactoring_lab.movieshop;

public class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String title;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    double amount(int daysRented) {
        // determine amount for each line
        switch (getPriceCode()) {
            case REGULAR: {
                if (daysRented > 2)
                    return 2 + (daysRented - 2) * 1.5;
                else
                    return 2;
            }
            case NEW_RELEASE: {
                return daysRented * 3;
            }
            case CHILDRENS: {
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
