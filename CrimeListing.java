import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

public class CrimeListing
{
    
    private String code;
    private String boroughName;
    private String year;
    private String typeOfOffence;
    private double rate;
    private int numberOfOffences;
    public CrimeListing(String code, String boroughName, String year,
                         String typeOfOffence, String neighbourhood, double rate, int numberOfOffences) 
    {
        this.code = code;
        this.boroughName = boroughName;
        this.year = year;
        this.typeOfOffence = typeOfOffence;
        this.rate = rate;
        this.numberOfOffences = numberOfOffences;
    }

    public String getCode() 
    {
        return code;
    }

    public String getBoroughName() 
    {
        return boroughName;
    }
    

    public String getYear() 
    {
        return year;
    }

    public String getTypeOfOffence() 
    {
        return typeOfOffence;
    }
    
    public double getRate() 
    {
        return rate;
    }

    public int getNumberOfOffences() 
    {
        return numberOfOffences;
    }


    @Override
    public String toString() 
    {
        return "CrimeListing{" +
                "code='" + code + '\'' +
                ", borough name='" + boroughName + '\'' +
                ", year='" + year + '\'' +
                ", typeOfOffence='" + typeOfOffence + '\'' +
                ", rate=" + rate +
                ", numberOfOffences=" + numberOfOffences +
                '}';
    }
    
  
    
    public static Comparator<CrimeListing> sortByNumberOfOffences = new Comparator<CrimeListing>() 
    {
        @Override
        public int compare(CrimeListing listingOne, CrimeListing listingTwo)
        {
            int listingOneNoOfOffences = listingOne.getNumberOfOffences();
            int listingTwoNoOfOffences  = listingTwo.getNumberOfOffences();
            
            return listingOneNoOfOffences - listingTwoNoOfOffences;
        }
    };
    
    public static Comparator<CrimeListing> sortByListingYear = new Comparator<CrimeListing>() 
    {
        @Override
        public int compare(CrimeListing listingOne, CrimeListing listingTwo)
        {
            String listingOneYear = listingOne.getYear();
            String listingTwoYear = listingTwo.getYear();
            

            return listingOneYear.compareTo(listingTwoYear);
        }
    };
    
    public static Comparator<CrimeListing> reverseSortByListingTypeOfOffence = new Comparator<CrimeListing>() 
    {
        @Override
        public int compare(CrimeListing listingOne, CrimeListing listingTwo)
        {
            String listingOneName = listingOne.getTypeOfOffence().toLowerCase();
            String listingTwoName = listingTwo.getTypeOfOffence().toLowerCase();
            
            return listingTwoName.compareTo(listingOneName);
        }
    };
    
    public static Comparator<CrimeListing> reverseSortByListingRate = new Comparator<CrimeListing>() 
    {
        @Override
        public int compare(CrimeListing listingOne, CrimeListing listingTwo)
        {
            Double listingOnePrice = listingOne.getRate();
            Double listingTwoPrice = listingTwo.getRate();
            
            return listingTwoPrice.compareTo(listingOnePrice);
        }
    };
    

}