package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public abstract class ExpandAggregateButton<T extends Node> extends NodeOptionButton<T> {
    
    public ExpandAggregateButton(String forExpandedAbNType, String forNodeType) {
        
        super("BluExpandedSubtaxonomy.png", 
                String.format("Created Expanded %s from Aggregate %s", forExpandedAbNType, forNodeType));
        
        this.addActionListener((ae) -> {
            expandAggregateAction();
        });
    }
    
    public abstract void expandAggregateAction();
}

