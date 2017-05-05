package edu.njit.cs.saboc.blu.core.gui.gep;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Chris O
 */
public class FloatingAbNDashboardFrame extends JInternalFrame {
    
    private final AbNExplorationPanel abnExplorationPanel;
    
    private final AbNDashboardPanel dashboardPanel;
    
    private final JToggleButton btnShowFullDetails;
    
    public FloatingAbNDashboardFrame(
            AbNExplorationPanel abnExplorationPanel, 
            AbNDashboardPanel dashboardPanel) {
        
        super("", true, false, false, false);
        
        this.abnExplorationPanel = abnExplorationPanel;
        this.dashboardPanel = dashboardPanel;
        
        JPanel internalPanel = new JPanel(new BorderLayout());
        
        internalPanel.add(dashboardPanel, BorderLayout.CENTER);
        
        btnShowFullDetails = new JToggleButton("Show Complete Details");
        
        btnShowFullDetails.addActionListener( (ae) -> { 
            if(btnShowFullDetails.isSelected()) {
                showFull();
            } else {
                showCompact();
            }
        });
        
        btnShowFullDetails.setSelected(true);
        
        JPanel southPanel = new JPanel();
        
        southPanel.add(btnShowFullDetails);
        
        internalPanel.add(southPanel, BorderLayout.SOUTH);
        
        this.add(internalPanel);

        this.setSize(520, 700);
        
        this.setMinimumSize(new Dimension(520, 150));
        
        showFull();
        
        JComponent titlePanel = (BasicInternalFrameTitlePane)((BasicInternalFrameUI)getUI()).getNorthPane();
        titlePanel.setPreferredSize(new Dimension(-1, 10));
        titlePanel.setBackground(new Color(100, 100, 255));
        
        this.setFrameIcon(null);
        
        this.setVisible(true);
    }
    
    private void showCompact() {
        this.setSize(this.getWidth(), Math.min(this.getHeight(), 300));
        
        dashboardPanel.setShowCompact(true);
    }
    
    private void showFull() {
        this.setSize(this.getWidth(), 700);
        
        dashboardPanel.setShowCompact(false);
        
        if(this.getY() + this.getHeight() > abnExplorationPanel.getHeight()) {
            
            int offset = abnExplorationPanel.getHeight() - this.getHeight() - 10;
            
            this.setLocation(this.getX(), offset);
        }
    }
}
