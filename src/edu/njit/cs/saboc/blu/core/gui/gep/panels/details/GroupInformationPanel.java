package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class GroupInformationPanel<T extends GenericConceptGroup> extends JPanel {
    public abstract void setContents(T group);
    
    public abstract void initUI();
    
    public abstract void clearContents();
}
