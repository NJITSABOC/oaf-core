package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class CreateTANButton extends BaseOptionButton {
    public CreateTANButton(String forNodeType) {
        super("BluTAN.png", String.format("Derive Tribal Abstraction Network from %s", forNodeType));
        
        this.addActionListener((ActionEvent ae) -> {
            deriveTANAction();
        });
    }
    
    public abstract void deriveTANAction();
}

