package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.BaseOptionButton;
import java.awt.event.ActionEvent;

/**
 *
 * @author Cros
 */

public abstract class CreateRootTANButton extends BaseOptionButton {
    
    public CreateRootTANButton() {
        super("BluSubtaxonomy.png", "Create a root TAN");
        
         this.addActionListener((ActionEvent ae) -> {
            createRootTANAction();
        });
    }

    public abstract void createRootTANAction();
}