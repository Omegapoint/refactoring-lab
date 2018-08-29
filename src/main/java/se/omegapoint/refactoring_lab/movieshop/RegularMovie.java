package se.omegapoint.refactoring_lab.movieshop;

public class RegularMovie extends Movie {

    public RegularMovie(String title) {
        super(title, REGULAR);
    }

    public double charge(int daysRented) {
        if (daysRented > 2)
            return 2.0 + (daysRented - 2) * 1.5;
        else
            return 2.0;
    }
}
