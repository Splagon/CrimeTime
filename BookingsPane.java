import javafx.scene.layout.Pane;

/**
 * Write a description of class BookingsPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BookingsPane extends MainViewerPane
{  
    private static Pane bookingPane;
    
    public BookingsPane(MainViewerNEW mainViewer)
    {
        super(mainViewer);
    }
    
    public void makePane() {
    }
    
    public Pane getPane() {
        return bookingPane;
    }
}
