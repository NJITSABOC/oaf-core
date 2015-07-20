package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class AbstractGroupHierarchyPanel<T extends GenericConceptGroup> extends GroupInformationPanel<T> {

    public AbstractGroupHierarchyPanel() {
        
    }
    
    @Override
    public void setContents(T group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
