package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodePanel;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public abstract class PopoutNodeDetailsButton extends BaseOptionButton {
    
    public PopoutNodeDetailsButton(String nodeType) {
        super("BluExpandWindow.png", String.format("Display %s details in new window", nodeType));
        
        this.addActionListener((ActionEvent ae) -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    displayDetailsWindow();
                }
            });
        });
    }
    
    private void displayDetailsWindow() {
        JDialog detailsDialog = new JDialog();
        detailsDialog.setSize(700, 600);
        
        detailsDialog.add(getCurrentDetailsPanel());
        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setVisible(true);
    }
    
    public abstract AbstractNodePanel getCurrentDetailsPanel();
}
