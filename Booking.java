import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * Write a description of class Booking here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Booking
{
    private AirbnbListing property;
    private String grandTotal;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int duration;

    /**
     * Constructor for objects of class Booking
     */
    public Booking(AirbnbListing property, String grandTotal, LocalDate checkInDate, LocalDate checkOutDate)
    {
        this.property = property;
        this.grandTotal = grandTotal;
        this.checkIn = checkIn;
        this.checkOut  = checkOut;
        duration = checkOutDate.compareTo(checkInDate);
    }
    
    private long calculateDuration(LocalDate checkInDate, LocalDate checkOutDate) {
        long days = Period.between(checkInDate, checkOutDate).getDays();
        return days;
    }

    public String getGrandTotal() {
        return grandTotal;
    }
    
    public AirbnbListing getProperty() {
        return property;
    }
    
    public LocalDate getCheckInDate() {
        return checkIn;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOut;
    }
    
    public int getDuration() {
        return duration;
    }
}
