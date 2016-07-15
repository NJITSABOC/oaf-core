package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public abstract class OpenBrowserButton<T extends Node> extends NodeOptionButton<T> {
    public OpenBrowserButton(String toolTip) {
        super("BluInvestigateRoot.png", toolTip);
        
        this.addActionListener((ae) -> {
            displayBrowserWindowAction();
        });
    }
    
    public abstract void displayBrowserWindowAction();
}
