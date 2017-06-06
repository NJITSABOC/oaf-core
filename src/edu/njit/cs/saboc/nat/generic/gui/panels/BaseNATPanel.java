package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.FocusConceptManager;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import javax.swing.JPanel;


/**
 * Base class for defining a panel in the NAT. All NAT panels should
 * extend this panel, as it provides access to the data source and main panel
 * 
 * @author cro3
 * @param <T> 
 */
public abstract class BaseNATPanel<T extends Concept> extends JPanel {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public BaseNATPanel(NATBrowserPanel<T> mainPanel) {
        this.mainPanel = mainPanel;
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }

    public void reset() {
        
    }
}
