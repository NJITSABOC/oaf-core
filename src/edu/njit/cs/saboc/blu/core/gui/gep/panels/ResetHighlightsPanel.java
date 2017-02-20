package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public class ResetHighlightsPanel extends AbNDisplayWidget {
    
     private final Dimension panelSize = new Dimension(120, 50);
    
    public ResetHighlightsPanel(AbNDisplayPanel panel) {
        super(panel);
        
        this.setLayout(new BorderLayout());
        
        JButton resetHighlightsButton = new JButton("<html><div align='center'>Clear Search Result Highlights");
        resetHighlightsButton.addActionListener( (ae) -> {
            panel.getAbNPainter().clearHighlights();
        });
        
        this.add(resetHighlightsButton, BorderLayout.CENTER);
    }

    @Override
    public void update(int tick) {
        AbNDisplayPanel panel = super.getDisplayPanel();
        
        if(panel.getAbNPainter().showingHighlights()) {
            this.setVisible(true);
        } else {
            this.setVisible(false);
        }
    }
    
    public void draw(AbNDisplayPanel display_panel){
        
        this.setBounds(display_panel.getWidth() / 2 - panelSize.width / 2, display_panel.getHeight() - panelSize.height - 20, panelSize.width, panelSize.height);
        display_panel.add(this);
        if(display_panel.getAbNPainter().showingHighlights()) {
            this.setVisible(true);
        } else {
            this.setVisible(false);
            display_panel.remove(this);
        }        
    }
    
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        this.setBounds(
                displayPanel.getWidth() - 400, 
                displayPanel.getHeight() - panelSize.height - 20, 
                panelSize.width, 
                panelSize.height);
    }
}
