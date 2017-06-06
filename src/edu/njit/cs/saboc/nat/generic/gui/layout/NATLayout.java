package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import javax.swing.JPanel;

/**
 * Base class for defining the location of result panels in the NAT
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class NATLayout<T extends Concept> extends JPanel {
    
    public NATLayout() {
    }
        
    public abstract void createLayout(NATBrowserPanel<T> mainPanel);
    
    public abstract void reset();
    
    public void resized(int newWidth, int newHeight) {
        
    }
}
