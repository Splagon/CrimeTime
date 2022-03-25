import javafx.scene.layout.Pane;

/**
 * Write a description of class MainViewerPane here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public abstract class MainViewerPane extends Pane
{
    //protected Pane pane = new Pane();
    protected MainViewer mainViewer;
    protected String titleName;
    protected boolean hasMinMaxBox;
    
    /**
     * Constructor for objects of class MainViewerPane
     */
    public MainViewerPane(MainViewer mainViewer)
    {
        this.mainViewer = mainViewer;
    }
    
    public abstract void makePane();
    
    public abstract Pane getPane();
    
    public String getTitleName() {
        return titleName;
    }
    
    public boolean getHasMinMaxBox() {
        return hasMinMaxBox;
    }
}
