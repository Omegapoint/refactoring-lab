package se.omegapoint.refactoring_lab.movieshop;

public class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    private final PriceCategoryImpl priceCategoryImpl = new PriceCategoryImpl();

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
        final int priceCode = getPriceCode();
        return priceCategoryImpl.amount(daysRented, priceCode);
    }

    private double amount(int daysRented, int priceCode) {
        return priceCategoryImpl.amount(daysRented, priceCode);
    }
}
