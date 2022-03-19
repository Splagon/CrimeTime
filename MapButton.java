import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class MapButton extends Button
{
    private String boroughName;
    
    public MapButton(String boroughName)
    {
        super(boroughName.substring(0, 4).toUpperCase());
        this.boroughName = boroughName;
        setTooltip(new Tooltip(boroughName));
    }
    
    public String getBoroughName() throws Exception {
        return boroughName;
    }
}
