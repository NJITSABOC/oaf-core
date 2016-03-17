package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.BaseOptionButton;
import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class CreateAncestorTANButton extends BaseOptionButton {

    public CreateAncestorTANButton() {
        super("BluAncestorSubtaxonomy.png", "Create an ancestor TAN");

        this.addActionListener((ActionEvent ae) -> {
            createAncestorTANAction();
        });

    }

    public abstract void createAncestorTANAction();
}
