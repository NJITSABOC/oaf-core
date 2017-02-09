package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class NATLayoutPanel<T extends Concept> extends JPanel {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public NATLayoutPanel(NATBrowserPanel<T> mainPanel) {
        this.mainPanel = mainPanel;
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    protected abstract void setFontSize(int fontSize);
}
