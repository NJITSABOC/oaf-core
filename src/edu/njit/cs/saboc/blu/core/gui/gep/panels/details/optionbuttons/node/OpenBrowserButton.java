package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class OpenBrowserButton<T extends SinglyRootedNode> extends NodeOptionButton<T> {
    
    public OpenBrowserButton(String toolTip) {
        super("BluInvestigateRoot.png", toolTip);
        
        this.addActionListener((ae) -> {
            displayBrowserWindowAction();
        });
    }
    
    public abstract void displayBrowserWindowAction();
}
