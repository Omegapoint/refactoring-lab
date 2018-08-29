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
        String result = "Rental Record for " + getName() + "\n";
        result += titleAndChargeForAllRentals();

        // add footer line
        result += "Amount owed is " + String.valueOf(totalAmount()) + "\n";
        result += "You earned " + String.valueOf(totalFrequentRenterPoints()) + " frequent renter points";
        return result;
    }

    private String titleAndChargeForAllRentals() {
        String rentalTitleAndCharges = "";
        for (Rental rental : rentals) {
            // show figures for this rental
            rentalTitleAndCharges += "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.movie.charge(rental.getDaysRented())) + "\n";
        }
        return rentalTitleAndCharges;
    }

    private int totalFrequentRenterPoints() {
        // add frequent renter points
        return rentals.stream().mapToInt(rental -> rental.frequentRenterPoints()).sum();
    }

    private double totalAmount() {
        return rentals.stream().mapToDouble(rental -> rental.getMovie().charge(rental.getDaysRented())).sum();
    }
}
