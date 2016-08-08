package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.NATOptions;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class NATLayoutPanel extends JPanel {
    
    private final GenericNATBrowser mainPanel;
    
    public NATLayoutPanel(GenericNATBrowser mainPanel) {
        this.mainPanel = mainPanel;
        
        NATOptions options = mainPanel.getOptions();
        
        options.addOptionsListener(new NATOptionsAdapter() {
            public void fontSizeChanged(int fontSize) {
                setFontSize(fontSize);
            }
        });
    }
    
    public GenericNATBrowser getMainPanel() {
        return mainPanel;
    }
    
    protected abstract void setFontSize(int fontSize);
}
