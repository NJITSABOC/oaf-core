package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNNodeInformationPanel extends JPanel {
    public abstract void setContents(Node node);
    public abstract void clearContents();
}
