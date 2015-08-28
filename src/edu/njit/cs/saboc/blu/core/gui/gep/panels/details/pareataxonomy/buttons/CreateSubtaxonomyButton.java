package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.BaseOptionButton;
import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class CreateSubtaxonomyButton extends BaseOptionButton {
    
    public CreateSubtaxonomyButton() {
        super("BluSubtaxonomy.png", "Create a root subtaxonomy");
        
         this.addActionListener((ActionEvent ae) -> {
            createSubtaxonomyAction();
        });
        
    }

    public abstract void createSubtaxonomyAction();
}
