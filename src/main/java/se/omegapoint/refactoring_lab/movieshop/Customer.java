package se.omegapoint.refactoring_lab.movieshop;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<Rental>();

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
        int frequentRenterPoints = 0;
        for (Rental rental : rentals) {
            // add frequent renter points

            frequentRenterPoints += rental.frequentRenterPoints();
        }

        String result = "Rental Record for " + getName() + "\n";
        for (Rental rental : rentals) {
            // show figures for this rental
            result += "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.charge()) + "\n";
        }

        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.charge();
        }
        // add footer line
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

}
