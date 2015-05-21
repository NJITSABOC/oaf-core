package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.ClassicLayoutPanel;
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
     
    private ClassicLayoutPanel layoutPanel;
    
    public GenericNATBrowser(JFrame parentFrame, ConceptBrowserDataSource dataSource) {
        this.setLayout(new BorderLayout());
               
        
        
        this.options = new Options();
        this.dataSource = dataSource;
        
        this.parentFrame = parentFrame;
                
        focusConcept = new FocusConcept(this, options, dataSource);
        
        initConceptBrowser(this);
                
        navigateTo(dataSource.getRoot());

        // Update
        focusConcept.updateAll();
    }

    public void navigateTo(BrowserConcept c) {
        focusConcept.navigate(c);
    }

    public void initConceptBrowser(JPanel panel) {
        this.layoutPanel = new ClassicLayoutPanel(this, dataSource);
        this.add(layoutPanel, BorderLayout.CENTER);
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
