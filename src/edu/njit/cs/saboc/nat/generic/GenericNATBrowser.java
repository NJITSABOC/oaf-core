package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.NATLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main panel (and top-level container of functionality) of the NAT
 * 
 * @param <T> The type of concept used in this NAT (e.g., OWL Class or SNOMED CT concept)
 */
public class GenericNATBrowser<T> extends JPanel {
    
    /**
     * Background color shared by all of the neighborhood panels
     */
    private final Color neighborhoodBGColor = new Color(150, 190, 220);

    /**
     * The data source used by every field in this NAT
     */
    private ConceptBrowserDataSource<T> dataSource;

    /**
     * The focus concept for this instance of a NAT
     */
    private FocusConcept<T> focusConcept;
    
    /**
     * The options for this NAT
     */
    private Options options;
   
    /**
     * The parent frame of this NAT
     */
    private JFrame parentFrame;
     
    /**
     * The layout of this NAT
     */
    private NATLayout layout;
    
    /**
     * 
     * @param parentFrame The top-level JFrame that this NAT belongs to
     * @param dataSource A data source
     * @param layout The visual layout of the NAT's elements
     */
    public GenericNATBrowser(JFrame parentFrame, ConceptBrowserDataSource<T> dataSource, NATLayout layout) {
        this.setLayout(new BorderLayout());
               
        this.options = new Options();
        this.dataSource = dataSource;
        
        this.parentFrame = parentFrame;
        
        this.layout = layout;
        
        this.add(layout, BorderLayout.CENTER);    
        
        focusConcept = new FocusConcept(this, options, dataSource);

        this.initConceptBrowser();
        this.navigateTo(dataSource.getRoot());
    }

    /**
     * Navigates the NAT to the 
     * @param c 
     */
    public void navigateTo(T c) {
        focusConcept.navigate(c);
    }

    /**
     * Initializes the layout for this NAT
     */
    public void initConceptBrowser() {
        layout.createLayout(this);
    }

    /**
     * 
     * @return The background color to be used by all neighborhood panels
     */
    public Color getNeighborhoodBGColor() {
        return neighborhoodBGColor;
    }

    /**
     * 
     * @return The options for this NAT
     */
    public Options getOptions() {
        return options;
    }

    /**
     * 
     * @return The Focus Concept for this NAT
     */
    public FocusConcept<T> getFocusConcept() {
        return focusConcept;
    }

    /**
     * 
     * @return The parent JFrame of this NAT
     */
    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    /**
     * 
     * @return The NATLayout of this NAT
     */
    public NATLayout getNATLayout() {
        return layout;
    }
}
