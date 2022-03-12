import javafx.scene.control.Button;

public class MapButton extends Button
{
    private String boroughName;
    
    public MapButton(String boroughName)
    {
        super(boroughName.substring(0, 4).toUpperCase());
        this.boroughName = boroughName;
    }
    
    public String getBoroughName() throws Exception {
        return boroughName;
    }
}
