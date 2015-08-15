package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

/**
 *
 * @author Chris O
 */
public abstract class AbstractNodeOptionsPanel<NODE_T> extends AbNNodeInformationPanel<NODE_T> {
    
    public AbstractNodeOptionsPanel() {
        
    }
    
    public abstract void enableOptionsForGroup(NODE_T group);
}
