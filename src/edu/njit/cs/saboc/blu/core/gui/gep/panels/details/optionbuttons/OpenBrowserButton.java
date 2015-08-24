package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public abstract class OpenBrowserButton extends BaseOptionButton {
    public OpenBrowserButton(String toolTip) {
        super("BluInvestigateRoot.png", toolTip);
        
        this.addActionListener((ActionEvent ae) -> {

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    displayBrowserWindow();
                }
            });
        });
    }
    
    public abstract void displayBrowserWindow();
}
