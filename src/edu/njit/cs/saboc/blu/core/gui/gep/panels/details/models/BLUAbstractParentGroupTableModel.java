package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;

/**
 *
 * @author Den
 */
public abstract class BLUAbstractParentGroupTableModel<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        T extends GenericParentGroupInfo<CONCEPT_T, GROUP_T>> extends BLUAbstractTableModel<T>{

    public BLUAbstractParentGroupTableModel() {
        
    }
    
    public CONCEPT_T getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public GROUP_T getParentGroup(int row) {
        return this.getItemAtRow(row).getParentGroup();
    }
}
