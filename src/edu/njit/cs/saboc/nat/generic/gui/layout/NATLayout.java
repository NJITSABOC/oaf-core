package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class NATLayout<CONCEPT_T, BROWSER_T extends GenericNATBrowser<CONCEPT_T>> extends JPanel{
    public abstract void createLayout(BROWSER_T mainPanel);
    
    public void handleResize(int newWidth, int newHeight) {
        
    }
}
