package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class BaseNodeInformationPanel<T extends Node> extends JPanel {
    
    public abstract void setContents(T node);
    public abstract void clearContents();
}
