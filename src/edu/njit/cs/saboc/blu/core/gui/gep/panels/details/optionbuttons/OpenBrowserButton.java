package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class OpenBrowserButton extends BaseOptionButton {
    public OpenBrowserButton(String toolTip) {
        super("BluInvestigateRoot.png", toolTip);
        
        this.addActionListener((ActionEvent ae) -> {
            displayBrowserWindowAction();
        });
    }
    
    public abstract void displayBrowserWindowAction();
}
