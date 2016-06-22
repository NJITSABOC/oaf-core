package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public abstract class BLUAbstractChildGroupTableModel<GROUP_T extends GenericConceptGroup> extends BLUAbstractTableModel<GROUP_T> {

    public BLUAbstractChildGroupTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public GROUP_T getChildGroup(int row) {
        return this.getItemAtRow(row);
    }
}
