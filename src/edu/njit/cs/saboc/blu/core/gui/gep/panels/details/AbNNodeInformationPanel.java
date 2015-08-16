package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNNodeInformationPanel<NODE_T> extends JPanel {
    public abstract void setContents(NODE_T group);

    public abstract void clearContents();
}
