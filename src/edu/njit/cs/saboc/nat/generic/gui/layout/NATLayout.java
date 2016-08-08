package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class NATLayout extends JPanel{
    
    public abstract void createLayout(GenericNATBrowser mainPanel);
    
    public void handleResize(int newWidth, int newHeight) {
        
    }
}
