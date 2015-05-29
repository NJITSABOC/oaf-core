package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.NATLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main class of the NAT.
 */
public class GenericNATBrowser<T> extends JPanel {
    
    private final Color neighborhoodBGColor = new Color(150, 190, 220);

    private ConceptBrowserDataSource<T> dataSource;

    private FocusConcept<T> focusConcept;
    private Options options;
   
    private JFrame parentFrame;
     
    private NATLayout layout;
    
    public GenericNATBrowser(JFrame parentFrame, ConceptBrowserDataSource<T> dataSource, NATLayout layout) {
        this.setLayout(new BorderLayout());
               
        this.options = new Options();
        this.dataSource = dataSource;
        
        this.parentFrame = parentFrame;
        
        this.layout = layout;
                
        focusConcept = new FocusConcept(this, options, dataSource);
        
        initConceptBrowser();
                
        navigateTo(dataSource.getRoot());

        // Update
        focusConcept.updateAll();
    }

    public void navigateTo(T c) {
        focusConcept.navigate(c);
    }

    public void initConceptBrowser() {
        this.add(layout.doLayout(this), BorderLayout.CENTER);
    }

    public Color getNeighborhoodBGColor() {
        return neighborhoodBGColor;
    }

    public Options getOptions() {
        return options;
    }

    public FocusConcept getFocusConcept() {
        return focusConcept;
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }
}
