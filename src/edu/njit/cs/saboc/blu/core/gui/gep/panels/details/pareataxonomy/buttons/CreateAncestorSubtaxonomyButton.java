package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.BaseOptionButton;
import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class CreateAncestorSubtaxonomyButton extends BaseOptionButton {

    public CreateAncestorSubtaxonomyButton() {
        super("BluAncestorSubtaxonomy.png", "Create an ancestor subtaxonomy");

        this.addActionListener((ActionEvent ae) -> {
            createSubtaxonomyAction();
        });

    }

    public abstract void createSubtaxonomyAction();
}
