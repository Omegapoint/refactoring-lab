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

    protected abstract int getPriceCode();
    protected abstract double charge(int daysRented);
}

class ChildrensMovie extends Movie {

    private String title;

    public ChildrensMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return CHILDRENS;
    }

    @Override
    protected double charge(int daysRented) {
        double amount = 1.5;
        if (daysRented > 3) {
            amount += (daysRented - 3) * 1.5;
        }
        return amount;
    }
}

class NewReleaseMovie extends Movie {

    private String title;

    public NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return NEW_RELEASE;
    }

    @Override
    protected double charge(int daysRented) {
        return daysRented * 3;
    }
}

class RegularMovie extends Movie {

    private String title;

    public RegularMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return REGULAR;
    }

    @Override
    protected double charge(int daysRented) {
        double thisAmount = 2;
        if (daysRented > 2) {
            thisAmount += (daysRented - 2) * 1.5;
        }
        return thisAmount;
    }
}
