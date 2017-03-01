package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class NATLayout<T extends Concept> extends JPanel {
    
    private final ConceptBrowserDataSource<T> dataSource;
    
    public NATLayout(ConceptBrowserDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }
    
    public abstract void createLayout(NATBrowserPanel<T> mainPanel);
    
    public void resized(int newWidth, int newHeight) {
        
    }
}
