import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

    @Test
    public void testStatement() {
        Customer tolvan = new Customer("Tolvan");
        tolvan.addRental(new Rental(new Movie("Fast and Furious 4711", Movie.NEW_RELEASE),2));
        tolvan.addRental(new Rental(new Movie("Toy Story 17", Movie.CHILDRENS),5));
        tolvan.addRental(new Rental(new Movie("Casa Blanca", Movie.REGULAR),1));
        String expected =
                "Rental Record for Tolvan\n" +
                "\tFast and Furious 4711\t6.0\n" +
                "\tToy Story 17\t4.5\n" +
                "\tCasa Blanca\t2.0\n" +
                "Amount owed is 12.5\n" +
                "You earned 4 frequent renter points";
        Assert.assertEquals(expected, tolvan.statement());
    }

}
