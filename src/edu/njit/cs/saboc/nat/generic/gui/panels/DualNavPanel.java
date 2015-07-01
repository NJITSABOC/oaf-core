package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.Options;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class DualNavPanel<T> extends NATLayoutPanel<T> {
    private final BaseNavPanel<T> primaryNavPanel;
    private final BaseNavPanel<T> secondaryNavPanel;
        
    private final JCheckBox secondaryFieldCheckBox;
    
    private final JPanel northPanel;
    
    private final String secondaryName;
    
    public DualNavPanel(GenericNATBrowser<T> mainPanel, String secondaryName, BaseNavPanel<T> primaryNavPanel, BaseNavPanel<T> secondaryNavPanel) {
        super(mainPanel);
        
        this.primaryNavPanel = primaryNavPanel;
        this.secondaryNavPanel = secondaryNavPanel;
        
        this.secondaryName = secondaryName;
        
        this.setLayout(new BorderLayout());
        
        final CardLayout cardLayout = new CardLayout();
        final JPanel centerPanel = new JPanel(cardLayout);
 
        centerPanel.add(primaryNavPanel, "PRIMARY");
        centerPanel.add(secondaryNavPanel, "SECONDARY");
        
        cardLayout.show(centerPanel, "PRIMARY");
        
        secondaryFieldCheckBox = new JCheckBox(String.format("Show %s", secondaryName));
        secondaryFieldCheckBox.addActionListener((ActionEvent e) -> {
            if (secondaryFieldCheckBox.isSelected()) {
                cardLayout.show(centerPanel, "SECONDARY");
                secondaryNavPanel.dataReady();
            } else {
                cardLayout.show(centerPanel, "PRIMARY");
                primaryNavPanel.dataReady();
            }
        });
                
        secondaryFieldCheckBox.setOpaque(false);
                       
        northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(mainPanel.getNeighborhoodBGColor());
        
        northPanel.add(secondaryFieldCheckBox, BorderLayout.WEST);
        
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }
    
    protected void setFontSize(int fontSize) {
        secondaryFieldCheckBox.setFont(secondaryFieldCheckBox.getFont().deriveFont(Font.BOLD, fontSize));
    }
    
    public void setOptionsMenuPanel(JPanel optionsPanel) {
        northPanel.add(optionsPanel, BorderLayout.EAST);
    }
    
    public void setInformationPanel(JPanel informationPanel) {
        northPanel.add(informationPanel, BorderLayout.CENTER);
    }
    
    public void setSecondaryCount(int count) {
        secondaryFieldCheckBox.setText(String.format("%s (%d)", secondaryName, count));
    }
            
    public boolean isSecondarySelected() {
        return secondaryFieldCheckBox.isSelected();
    }
}
