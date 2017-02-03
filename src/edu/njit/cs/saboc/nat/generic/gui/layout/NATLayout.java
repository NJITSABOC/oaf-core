package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class NATLayout extends JPanel {
    
    private final ConceptBrowserDataSource dataSource;
    
    public NATLayout(ConceptBrowserDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public ConceptBrowserDataSource getDataSource() {
        return dataSource;
    }
    
    public abstract void createLayout(GenericNATBrowserPanel mainPanel);
    
    public void resized(int newWidth, int newHeight) {
        
    }
}
