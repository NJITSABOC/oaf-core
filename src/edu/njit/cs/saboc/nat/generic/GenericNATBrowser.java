package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.ClassicLayout;
import edu.njit.cs.saboc.nat.generic.gui.layout.NATLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main class of the NAT.
 */
public class GenericNATBrowser extends JPanel {
    
    private final Color neighborhoodBGColor = new Color(150, 190, 220);

    private ConceptBrowserDataSource dataSource;

    private FocusConcept focusConcept;
    private Options options;
   
    private JFrame parentFrame;
     
    private NATLayout layout;
    
    public GenericNATBrowser(JFrame parentFrame, ConceptBrowserDataSource dataSource, NATLayout layout) {
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
    
    public GenericNATBrowser(JFrame parentFrame, ConceptBrowserDataSource dataSource) {
        this(parentFrame, dataSource, new ClassicLayout(dataSource));
    }

    public void navigateTo(BrowserConcept c) {
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
