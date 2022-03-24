import javafx.scene.layout.Pane;

/**
 * Write a description of class MainViewerPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class MainViewerPane extends Pane
{
    //protected Pane pane = new Pane();
    protected MainViewerNEW mainViewer;
    
    /**
     * Constructor for objects of class MainViewerPane
     */
    public MainViewerPane(MainViewerNEW mainViewer)
    {
        this.mainViewer = mainViewer;
    }
    
    public abstract void makePane();
    
    public abstract Pane getPane();
}
