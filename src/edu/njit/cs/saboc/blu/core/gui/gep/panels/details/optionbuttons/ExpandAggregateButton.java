package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 */
public abstract class ExpandAggregateButton extends NodeOptionButton {
    public ExpandAggregateButton(String forExpandedAbNType, String forNodeType) {
        super("BluExpandedSubtaxonomy.png", String.format("Created Expanded %s from Aggregate %s", forExpandedAbNType, forNodeType));
        
        this.addActionListener((ActionEvent ae) -> {
            expandAggregateAction();
        });
    }
    
    public abstract void expandAggregateAction();
}

