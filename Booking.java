import java.time.LocalDate;
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
    public Booking(AirbnbListing property, String grandTotal, String checkInDate, String checkOutDate)
    {
        this.property = property;
        this.grandTotal = grandTotal;
        this.checkIn = checkIn;
        this.checkOut  = checkOut;
        duration = checkOut.compareTo(checkIn);
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
    
    public int getgrandTotalDuration() {
        return duration;
    }
}
