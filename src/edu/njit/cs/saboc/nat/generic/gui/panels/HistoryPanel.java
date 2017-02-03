package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowserPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class HistoryPanel<T extends Concept> extends NATLayoutPanel  {
    
    public HistoryPanel(GenericNATBrowserPanel<T> mainBrowserPanel) {
        super(mainBrowserPanel);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
    
}
