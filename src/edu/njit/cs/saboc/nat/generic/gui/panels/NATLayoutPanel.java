package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.NATOptions;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class NATLayoutPanel<T> extends JPanel {
    
    protected final GenericNATBrowser<T> mainPanel;
    
    public NATLayoutPanel(GenericNATBrowser<T> mainPanel) {
        this.mainPanel = mainPanel;
        
        NATOptions options = mainPanel.getOptions();
        
        options.addOptionsListener(new NATOptionsAdapter() {
            public void fontSizeChanged(int fontSize) {
                setFontSize(fontSize);
            }
        });
    }
    
    protected abstract void setFontSize(int fontSize);

}
