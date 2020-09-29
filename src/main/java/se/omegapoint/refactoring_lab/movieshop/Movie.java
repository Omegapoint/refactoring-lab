package se.omegapoint.refactoring_lab.movieshop;

public class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String title;
    private int priceCode;
    private PriceStrategy priceStrategy;

    public Movie(String title, int priceCode, PriceStrategy priceStrategy) {
        this.title = title;
        this.priceCode = priceCode;
        this.priceStrategy = priceStrategy;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    public void setPriceStrategy(PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    public String getTitle() {
        return title;
    }

    double amount(int daysRented) {
        return priceStrategy.amount(daysRented);
    }
}
