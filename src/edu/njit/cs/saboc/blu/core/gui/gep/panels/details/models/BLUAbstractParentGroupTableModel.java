package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;

/**
 *
 * @author Den
 */
public abstract class BLUAbstractParentGroupTableModel<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        T extends ParentNodeInformation<CONCEPT_T, GROUP_T>> extends BLUAbstractTableModel<T> {

    public BLUAbstractParentGroupTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public CONCEPT_T getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public GROUP_T getParentGroup(int row) {
        return this.getItemAtRow(row).getParentGroup();
    }
}
