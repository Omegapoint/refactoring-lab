package se.omegapoint.refactoring_lab.movieshop;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

    @Test
    public void statement_should_include_descriptions_and_points_and_total_amount() {
        Customer tolvan = new Customer("Tolvan");
        tolvan.addRental(new Rental(new Movie("Fast and Furious 4711", Movie.NEW_RELEASE, new NewReleasePriceStrategy()),2));
        tolvan.addRental(new Rental(new Movie("Toy Story 17", Movie.CHILDRENS, new ChildrensPriceStrategy()),5));
        tolvan.addRental(new Rental(new Movie("Casa Blanca", Movie.REGULAR, new RegularPriceStrategy()),1));
        String expected =
                "Rental Record for Tolvan\n" +
                "\tFast and Furious 4711\t6.0\n" +
                "\tToy Story 17\t4.5\n" +
                "\tCasa Blanca\t2.0\n" +
                "Amount owed is 12.5\n" +
                "You earned 4 frequent renter points";
        Assert.assertEquals(expected, tolvan.statement());
    }

    @Test
    public void setPriceCode_should_change_statement() {
        Customer tolvan = new Customer("Tolvan");
        final Movie dieHard = new Movie("Die Hard", Movie.CHILDRENS, new ChildrensPriceStrategy());
        tolvan.addRental(new Rental(dieHard,5));
        Assert.assertEquals("Rental Record for Tolvan\n" +
                "\tDie Hard\t4.5\n" +
                "Amount owed is 4.5\n" +
                "You earned 1 frequent renter points", tolvan.statement());
        dieHard.setPriceCode(Movie.NEW_RELEASE);
        dieHard.setPriceStrategy(new NewReleasePriceStrategy());
        Assert.assertEquals("Rental Record for Tolvan\n" +
                "\tDie Hard\t15.0\n" +
                "Amount owed is 15.0\n" +
                "You earned 2 frequent renter points", tolvan.statement());
    }
}
