package se.omegapoint.refactoring_lab.movieshop;

public abstract class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String title;

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract int getPriceCode();

    public abstract double charge(int daysRented);
}
