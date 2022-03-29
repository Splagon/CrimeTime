import javafx.scene.layout.Pane;

/**
 * The super class of all panes which are used in the mainViewer
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 29/03/22
 */
public abstract class MainViewerPane extends Pane
{
    //protected Pane pane = new Pane();
    protected MainViewer mainViewer;
    protected String titleName;
    protected boolean hasMinMaxBox;
    
    /**
     * Constructor for objects of class MainViewerPane
     * 
     * @param mainViewer The parent of the classpane
     */
    public MainViewerPane(MainViewer mainViewer)
    {
        this.mainViewer = mainViewer;
    }
    
    /**
     * Builds the map pane
     */
    public abstract void makePane();
    
    public abstract Pane getPane();
    
    /**
     * @return String titleName the title of the pane
     */
    public String getTitleName() 
    {
        return titleName;
    }
    
    /**
     * @return Returns whether the min max combobox should appear in the top
     *         right of the screen.
     */
    public boolean getHasMinMaxBox() 
    {
        return hasMinMaxBox;
    }
}
