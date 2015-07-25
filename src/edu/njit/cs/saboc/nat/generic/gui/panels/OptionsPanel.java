package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.panels.options.FontSizeOptionPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.options.OptionPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class OptionsPanel<T> extends NATLayoutPanel<T> {

    private final ArrayList<OptionPanel<T>> optionPanels = new ArrayList<>();
    
    private final JPanel optionsListPanel;

    public OptionsPanel(GenericNATBrowser<T> mainPanel) {
        super(mainPanel);

        this.setLayout(new BorderLayout());
        
        optionsListPanel = new JPanel();
        optionsListPanel.setLayout(new BoxLayout(optionsListPanel, BoxLayout.Y_AXIS));
        
        this.add(optionsListPanel, BorderLayout.NORTH);

        this.addOptionsPanel(new FontSizeOptionPanel<T>(mainPanel));
    }
    
    public void addOptionsPanel(OptionPanel<T> optionsPanel) {
        optionPanels.add(optionsPanel);
        
        optionsListPanel.add(optionsPanel);
    }

    protected void setFontSize(int fontSize) {
        
    }
}
