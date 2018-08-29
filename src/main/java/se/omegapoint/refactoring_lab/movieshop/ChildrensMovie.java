package se.omegapoint.refactoring_lab.movieshop;

public class ChildrensMovie extends Movie {
    public ChildrensMovie(String title) {
        super(title, CHILDRENS);
    }

    public double charge(int daysRented) {
        if (daysRented > 3)
            return 1.5 + (daysRented - 3) * 1.5;
        else
            return 1.5;
    }
}
