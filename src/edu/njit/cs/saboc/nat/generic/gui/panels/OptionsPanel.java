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
public class OptionsPanel extends NATLayoutPanel {

    private final ArrayList<OptionPanel> optionPanels = new ArrayList<>();
    
    private final JPanel optionsListPanel;

    public OptionsPanel(GenericNATBrowser mainPanel) {
        super(mainPanel);

        this.setLayout(new BorderLayout());
        
        optionsListPanel = new JPanel();
        optionsListPanel.setLayout(new BoxLayout(optionsListPanel, BoxLayout.Y_AXIS));
        
        this.add(optionsListPanel, BorderLayout.NORTH);

        this.addOptionsPanel(new FontSizeOptionPanel(mainPanel));
    }
    
    public final void addOptionsPanel(OptionPanel optionsPanel) {
        optionPanels.add(optionsPanel);
        
        optionsListPanel.add(optionsPanel);
    }

    protected void setFontSize(int fontSize) {
        
    }
}
