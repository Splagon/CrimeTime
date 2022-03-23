import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private int grandTotal;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private long duration;

    /**
     * Constructor for objects of class Booking
     */
    public Booking(AirbnbListing property, int grandTotal, LocalDate checkInDate, LocalDate checkOutDate)
    {
        this.property = property;
        this.grandTotal = grandTotal;
        this.checkIn = checkInDate;
        this.checkOut  = checkOutDate;
        duration = calculateDuration(checkInDate, checkOutDate);
    }
    
    public static long calculateDuration(LocalDate checkInDate, LocalDate checkOutDate) {
        //Period period = Period.between(checkInDate, checkOutDate);
        long days = checkInDate.until(checkOutDate, ChronoUnit.DAYS);
        return days;
    }

    public int getGrandTotal() {
        return grandTotal;
    }
    
    public AirbnbListing getProperty() {
        return property;
    }
    
    public String getPropertyID() {
        return property.getId();
    }
    
    public LocalDate getCheckInDate() {
        return checkIn;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOut;
    }
    
    public long getDuration() {
        return duration;
    }
}
