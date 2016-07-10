package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class CreateDisjointAbNButton extends NodeOptionButton {
    public CreateDisjointAbNButton(String toolTip) {
        super("BluDisjointAbN.png", toolTip);
        
        this.addActionListener((ActionEvent ae) -> {
            createDisjointAbNAction();
        });
    }
    
    public abstract void createDisjointAbNAction();
}
