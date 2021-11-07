package se.omegapoint.refactoring_lab.movieshop;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        String result = "Rental Record for " + getName() + "\n";
        result += getRentalFigures();
        // add footer line
        result += "Amount owed is " + getTotalCharge() + "\n";
        result += "You earned " + getTotalPoints() + " frequent renter points";
        return result;
    }

    private String getRentalFigures() {
        String tmpResult = "";
        for (Rental each : rentals) {
            // show figures for this rental
            tmpResult += "\t" + each.getMovie().getTitle() + "\t" + each.charge() + "\n";
        }
        return tmpResult;
    }

    private int getTotalPoints() {
        int tmpPoints = 0;
        for (Rental each : rentals) {
            tmpPoints += each.points();
        }
        return tmpPoints;
    }

    private double getTotalCharge() {
        double tmpAmount = 0;
        for (Rental each : rentals) {
            tmpAmount += each.charge();
        }
        return tmpAmount;
    }

}
